package com.ps.app.coupon.application.port.`in`

import com.ps.app.coupon.domain.constant.DiscountType
import java.time.LocalDate

data class CreateCouponPolicyCommand(
    val couponTypeId: Int,
    val name: String,
    val discountType: DiscountType,
    val discountRate: Double = 0.0,
    val discountAmount: Int = 0,
    val period: Int,
    val standardPrice: Int,
    val maxDiscountAmount: Int,
    val startDate: LocalDate,
    val endDate: LocalDate
)
