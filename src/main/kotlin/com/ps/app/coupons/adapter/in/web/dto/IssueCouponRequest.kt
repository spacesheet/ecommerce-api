package com.ps.app.coupons.adapter.`in`.web.dto

/**
 * 쿠폰 발급 요청 DTO
 */
data class IssueCouponRequest(
    val ownerId: Long,
    val couponPolicyId: Int,
    val couponCode: String,
    val validDays: Int
)
