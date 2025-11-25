package com.ps.app.coupons.application.port.`in`

data class UseCouponCommand(
    val couponId: Long,
    val userId: Long
)
