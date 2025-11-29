package com.ps.app.coupons.domain

import com.ps.app.coupons.domain.constant.DiscountType
import java.time.LocalDate

data class CouponPolicy(
    val id: CouponPolicyId = CouponPolicyId.NEW,
    val couponType: CouponType,
    val name: String,
    val discountType: DiscountType,
    val discountRate: Double,
    val discountAmount: Int,
    val period: Int,
    val standardPrice: Int,
    val maxDiscountAmount: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val deleted: Boolean = false
) {
    init {
        require(name.isNotBlank()) { "Coupon policy name cannot be blank" }
        require(name.length <= 30) { "Coupon policy name must be 30 characters or less" }
        require(discountRate >= 0) { "Discount rate cannot be negative" }
        require(discountAmount >= 0) { "Discount amount cannot be negative" }
        require(period > 0) { "Period must be positive" }
        require(standardPrice >= 0) { "Standard price cannot be negative" }
        require(maxDiscountAmount >= 0) { "Max discount amount cannot be negative" }
        require(endDate >= startDate) { "End date must be after or equal to start date" }

        when (discountType) {
            DiscountType.PERCENTAGE -> {
                require(discountRate > 0) { "Discount rate must be positive for PERCENTAGE type" }
                require(discountRate <= 100) { "Discount rate cannot exceed 100%" }
            }
            DiscountType.FIXED_AMOUNT -> {
                require(discountAmount > 0) { "Discount amount must be positive for FIXED_AMOUNT type" }
            }
        }
    }

    fun isNew(): Boolean = id.isNew()

    fun changeEndDate(newEndDate: LocalDate): CouponPolicy {
        require(newEndDate >= startDate) { "End date must be after or equal to start date" }
        require(!deleted) { "Cannot modify deleted policy" }
        return copy(endDate = newEndDate)
    }

    fun delete(): CouponPolicy {
        require(!deleted) { "Policy is already deleted" }
        return copy(deleted = true)
    }

    fun isActive(): Boolean {
        val now = LocalDate.now()
        return !deleted && now >= startDate && now <= endDate
    }

    fun isExpired(): Boolean {
        return LocalDate.now().isAfter(endDate)
    }

    fun calculateDiscount(originalPrice: Int): Int {
        require(originalPrice >= 0) { "Original price cannot be negative" }
        require(!deleted) { "Cannot use deleted policy" }
        require(isActive()) { "Cannot use inactive policy" }
        require(originalPrice >= standardPrice) {
            "Original price must be at least $standardPrice to apply this coupon"
        }

        val calculatedDiscount = when (discountType) {
            DiscountType.PERCENTAGE -> {
                (originalPrice * discountRate / 100).toInt()
            }
            DiscountType.FIXED_AMOUNT -> {
                discountAmount
            }
        }

        return minOf(calculatedDiscount, maxDiscountAmount)
    }

    fun calculateFinalPrice(originalPrice: Int): Int {
        val discount = calculateDiscount(originalPrice)
        return maxOf(0, originalPrice - discount)
    }

    fun isPercentageDiscount(): Boolean = discountType == DiscountType.PERCENTAGE

    fun isFixedAmountDiscount(): Boolean = discountType == DiscountType.FIXED_AMOUNT

    fun canApplyTo(orderAmount: Int): Boolean {
        return isActive() && orderAmount >= standardPrice
    }

    companion object {
        fun create(
            couponType: CouponType,
            name: String,
            discountType: DiscountType,
            discountRate: Double = 0.0,
            discountAmount: Int = 0,
            period: Int,
            standardPrice: Int,
            maxDiscountAmount: Int,
            startDate: LocalDate,
            endDate: LocalDate
        ): CouponPolicy {
            return CouponPolicy(
                id = CouponPolicyId.NEW,
                couponType = couponType,
                name = name,
                discountType = discountType,
                discountRate = discountRate,
                discountAmount = discountAmount,
                period = period,
                standardPrice = standardPrice,
                maxDiscountAmount = maxDiscountAmount,
                startDate = startDate,
                endDate = endDate,
                deleted = false
            )
        }
    }
}

