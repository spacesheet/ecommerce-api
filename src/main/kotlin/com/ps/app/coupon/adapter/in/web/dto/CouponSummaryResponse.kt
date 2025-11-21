package com.ps.app.coupon.adapter.`in`.web.dto

import com.ps.app.coupon.domain.Coupon
import com.ps.app.coupon.domain.constant.CouponStatus

/**
 * 쿠폰 요약 정보 응답 DTO
 * 목록 조회 시 사용
 */
data class CouponSummaryResponse(
    val id: Long?,
    val couponCode: String,
    val status: CouponStatus,
    val daysUntilExpiration: Long?
) {
    companion object {
        fun from(coupon: Coupon): CouponSummaryResponse {
            val daysUntilExpiration = if (!coupon.isExpired()) {
                java.time.temporal.ChronoUnit.DAYS.between(
                    java.time.LocalDate.now(),
                    coupon.expireDate
                )
            } else null

            return CouponSummaryResponse(
                id = coupon.id,
                couponCode = coupon.couponCode,
                status = coupon.status,
                daysUntilExpiration = daysUntilExpiration
            )
        }
    }
}
