package com.ps.app.coupons.domain

import com.ps.app.coupons.domain.constant.DiscountType
import java.time.LocalDate

data class PercentCouponPolicy(
    private val policyId: CouponPolicyId = CouponPolicyId.NEW,
    private val policyType: CouponType,
    private val policyName: String,
    val discountRate: Double,
    private val policyConditions: List<CouponCondition>,
    private val isDeleted: Boolean = false
) : CouponPolicy(
    id = policyId,
    couponType = policyType,
    name = policyName,
    conditions = policyConditions.toTypedArray(),
    deleted = isDeleted
) {
    init {
        require(discountRate > 0) { "Discount rate must be positive for percentage coupon" }
        require(discountRate <= 100) { "Discount rate cannot exceed 100%" }
    }

    override val discountType: DiscountType = DiscountType.PERCENTAGE

    override fun calculateDiscountAmount(originalPrice: Int): Int {
        return (originalPrice * discountRate / 100).toInt()
    }

    override fun createWithNewConditions(newConditions: List<CouponCondition>): CouponPolicy {
        return copy(policyConditions = newConditions)
    }

    override fun markAsDeleted(): CouponPolicy {
        return copy(isDeleted = true)
    }

    companion object {
        fun create(
            couponType: CouponType,
            name: String,
            discountRate: Double,
            vararg conditions: CouponCondition
        ): PercentCouponPolicy {
            require(conditions.isNotEmpty()) { "At least one condition is required" }

            return PercentCouponPolicy(
                policyId = CouponPolicyId.NEW,
                policyType = couponType,
                policyName = name,
                discountRate = discountRate,
                policyConditions = conditions.toList(),
                isDeleted = false
            )
        }

        fun createWithSingleCondition(
            couponType: CouponType,
            name: String,
            discountRate: Double,
            period: Int,
            standardPrice: Int,
            maxDiscountAmount: Int,
            startDate: LocalDate,
            endDate: LocalDate
        ): PercentCouponPolicy {
            val condition = CouponCondition(
                period = period,
                standardPrice = standardPrice,
                maxDiscountAmount = maxDiscountAmount,
                startDate = startDate,
                endDate = endDate
            )

            return create(couponType, name, discountRate, condition)
        }
    }
}
