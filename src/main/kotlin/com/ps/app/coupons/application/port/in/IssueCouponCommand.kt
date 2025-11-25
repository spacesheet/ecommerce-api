package com.ps.app.coupons.application.port.`in`

data class IssueCouponCommand(
    val ownerId: Long,
    val couponPolicyId: Long,
    val couponCode: String,
    val validDays: Int
)
