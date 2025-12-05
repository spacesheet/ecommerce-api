package com.ps.app.coupons.domain

import com.ps.app.coupons.domain.constant.DiscountType
import java.time.LocalDate

abstract class CouponPolicy(
    val id: CouponPolicyId = CouponPolicyId.NEW,
    val couponType: CouponType,
    val name: String,
    vararg conditions: CouponCondition,
    val deleted: Boolean = false
) {
    val conditions: List<CouponCondition> = conditions.toList()

    init {
        require(name.isNotBlank()) { "Coupon policy name cannot be blank" }
        require(name.length <= 30) { "Coupon policy name must be 30 characters or less" }
        require(this.conditions.isNotEmpty()) { "At least one condition is required" }
    }

    abstract val discountType: DiscountType

    abstract fun calculateDiscountAmount(originalPrice: Int): Int

    fun isNew(): Boolean = id.isNew()

    fun changeEndDate(newEndDate: LocalDate): CouponPolicy {
        require(!deleted) { "Cannot modify deleted policy" }
        val newConditions = conditions.map { it.changeEndDate(newEndDate) }
        return createWithNewConditions(newConditions)
    }

    abstract fun createWithNewConditions(newConditions: List<CouponCondition>): CouponPolicy

    abstract fun markAsDeleted(): CouponPolicy

    fun delete(): CouponPolicy {
        require(!deleted) { "Policy is already deleted" }
        return markAsDeleted()
    }

    fun isActive(): Boolean {
        return !deleted && conditions.any { it.isActive() }
    }

    fun isExpired(): Boolean {
        return conditions.all { it.isExpired() }
    }

    fun getActiveConditions(): List<CouponCondition> {
        return conditions.filter { it.isActive() }
    }

    fun calculateDiscount(originalPrice: Int): Int {
        require(originalPrice >= 0) { "Original price cannot be negative" }
        require(!deleted) { "Cannot use deleted policy" }
        require(isActive()) { "Cannot use inactive policy" }

        val applicableCondition = findApplicableCondition(originalPrice)
            ?: throw IllegalStateException("No applicable condition found for price: $originalPrice")

        val calculatedDiscount = calculateDiscountAmount(originalPrice)
        return minOf(calculatedDiscount, applicableCondition.maxDiscountAmount)
    }

    fun calculateFinalPrice(originalPrice: Int): Int {
        val discount = calculateDiscount(originalPrice)
        return maxOf(0, originalPrice - discount)
    }

    fun canApplyTo(orderAmount: Int): Boolean {
        return isActive() && conditions.any { it.canApplyTo(orderAmount) }
    }

    fun findApplicableCondition(orderAmount: Int): CouponCondition? {
        return getActiveConditions()
            .filter { it.canApplyTo(orderAmount) }
            .maxByOrNull { it.standardPrice } // 가장 조건이 높은 것을 선택
    }

    fun getAllPeriods(): List<Int> = conditions.map { it.period }.distinct()

    fun getMaxDiscountAmount(): Int = conditions.maxOfOrNull { it.maxDiscountAmount } ?: 0

    fun getMinStandardPrice(): Int = conditions.minOfOrNull { it.standardPrice } ?: 0
}
