package com.ps.app.coupons.domain

import com.ps.app.coupons.domain.constant.DiscountType
import java.time.LocalDate

data class AmountCouponPolicy(
    private val policyId: CouponPolicyId = CouponPolicyId.NEW,
    private val policyType: CouponType,
    private val policyName: String,
    val discountAmount: Int,
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
        require(discountAmount > 0) { "Discount amount must be positive for fixed amount coupon" }
    }

    override val discountType: DiscountType = DiscountType.FIXED_AMOUNT

    override fun calculateDiscountAmount(originalPrice: Int): Int {
        return discountAmount
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
            discountAmount: Int,
            vararg conditions: CouponCondition
        ): AmountCouponPolicy {
            require(conditions.isNotEmpty()) { "At least one condition is required" }

            return AmountCouponPolicy(
                policyId = CouponPolicyId.NEW,
                policyType = couponType,
                policyName = name,
                discountAmount = discountAmount,
                policyConditions = conditions.toList(),
                isDeleted = false
            )
        }

        fun createWithSingleCondition(
            couponType: CouponType,
            name: String,
            discountAmount: Int,
            period: Int,
            standardPrice: Int,
            maxDiscountAmount: Int,
            startDate: LocalDate,
            endDate: LocalDate
        ): AmountCouponPolicy {
            val condition = CouponCondition(
                period = period,
                standardPrice = standardPrice,
                maxDiscountAmount = maxDiscountAmount,
                startDate = startDate,
                endDate = endDate
            )

            return create(couponType, name, discountAmount, condition)
        }
    }
}
