package com.ps.app.coupons.domain

import com.ps.app.coupons.domain.constant.DiscountType
import java.time.LocalDate

object NoneCouponPolicy : CouponPolicy(
    id = CouponPolicyId.NONE,
    couponType = CouponType.createGlobal(),
    name = "쿠폰 미적용",
    conditions = arrayOf(
        CouponCondition(
            id = CouponConditionId.NONE,
            period = Int.MAX_VALUE,
            standardPrice = 0,
            maxDiscountAmount = 0,
            startDate = LocalDate.MIN,
            endDate = LocalDate.MAX
        )
    ),
    deleted = false
) {
    override val discountType: DiscountType = DiscountType.FIXED_AMOUNT

    override fun calculateDiscountAmount(originalPrice: Int): Int = 0

    override fun createWithNewConditions(newConditions: List<CouponCondition>): CouponPolicy = this

    override fun markAsDeleted(): CouponPolicy = this

    override fun isActive(): Boolean = true

    override fun calculateDiscount(originalPrice: Int): Int {
        require(originalPrice >= 0) { "Original price cannot be negative" }
        return 0
    }

    override fun calculateFinalPrice(originalPrice: Int): Int {
        require(originalPrice >= 0) { "Original price cannot be negative" }
        return originalPrice
    }

    override fun canApplyTo(orderAmount: Int): Boolean = true
}
