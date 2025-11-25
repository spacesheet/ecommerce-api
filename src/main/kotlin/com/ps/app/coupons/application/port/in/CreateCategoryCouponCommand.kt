package com.ps.app.coupons.application.port.`in`

data class CreateCategoryCouponCommand(
    val couponPolicyId: Int,
    val categoryId: Int
)
