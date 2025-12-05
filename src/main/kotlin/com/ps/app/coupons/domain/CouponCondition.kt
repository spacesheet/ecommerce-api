package com.ps.app.coupons.domain

import java.time.LocalDate

data class CouponCondition(
    val id: CouponConditionId = CouponConditionId.NEW,
    val period: Int,
    val standardPrice: Int,
    val maxDiscountAmount: Int,
    val startDate: LocalDate,
    val endDate: LocalDate
) {
    init {
        require(period > 0) { "Period must be positive" }
        require(standardPrice >= 0) { "Standard price cannot be negative" }
        require(maxDiscountAmount >= 0) { "Max discount amount cannot be negative" }
        require(endDate >= startDate) { "End date must be after or equal to start date" }
    }

    fun isNew(): Boolean = id.isNew()

    fun isActive(): Boolean {
        val now = LocalDate.now()
        return now in startDate..endDate
    }

    fun isExpired(): Boolean {
        return LocalDate.now().isAfter(endDate)
    }

    fun canApplyTo(orderAmount: Int): Boolean {
        return isActive() && orderAmount >= standardPrice
    }

    fun changeEndDate(newEndDate: LocalDate): CouponCondition {
        require(newEndDate >= startDate) { "End date must be after or equal to start date" }
        return copy(endDate = newEndDate)
    }
}

