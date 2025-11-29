package com.ps.app.coupons.domain

import java.time.LocalDate

/**
 * 쿠폰 요약 정보
 */
data class CouponSummary(
    val couponCode: String,
    val policyName: String,
    val couponScope: String,
    val discountType: String,
    val discountRate: Double,
    val discountAmount: Int,
    val minOrderAmount: Int,
    val maxDiscountAmount: Int,
    val expireDate: LocalDate,
    val status: String,
    val isAvailable: Boolean
)
