package com.ps.app.coupons.adapter.`in`.web.dto

import com.ps.app.coupons.domain.Coupons
import com.ps.app.coupons.domain.constant.CouponStatus

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
        fun from(coupons: Coupons): CouponSummaryResponse {
            val daysUntilExpiration = if (!coupons.isExpired()) {
                java.time.temporal.ChronoUnit.DAYS.between(
                    java.time.LocalDate.now(),
                    coupons.expireDate
                )
            } else null

            return CouponSummaryResponse(
                id = coupons.id.value,
                couponCode = coupons.couponCode,
                status = coupons.couponStatus,
                daysUntilExpiration = daysUntilExpiration
            )
        }
    }
}
