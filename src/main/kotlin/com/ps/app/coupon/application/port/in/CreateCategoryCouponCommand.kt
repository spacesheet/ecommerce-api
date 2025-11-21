package com.ps.app.coupon.application.port.`in`

data class CreateCategoryCouponCommand(
    val couponPolicyId: Int,
    val categoryId: Int
)
