package com.ps.app.coupon.application.port.`in`

data class UseCouponCommand(
    val couponId: Long,
    val userId: Long
)
