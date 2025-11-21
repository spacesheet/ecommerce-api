package com.ps.app.coupon.adapter.`in`.web.dto

import com.ps.app.coupon.domain.Coupon
import com.ps.app.coupon.domain.constant.CouponStatus
import java.time.LocalDate

/**
 * 쿠폰 응답 DTO
 * Primary Adapter의 응답 객체
 */
data class CouponResponse(
    val id: Long?,
    val ownerId: Long,
    val couponPolicyId: Long,
    val couponCode: String,
    val createDate: LocalDate,
    val expireDate: LocalDate,
    val status: CouponStatus,
    val isExpired: Boolean,
    val isAvailable: Boolean
) {
    companion object {
        /**
         * Coupon 도메인 모델을 CouponResponse로 변환
         */
        fun from(coupon: Coupon): CouponResponse {
            return CouponResponse(
                id = coupon.id,
                ownerId = coupon.ownerId,
                couponPolicyId = coupon.couponPolicyId,
                couponCode = coupon.couponCode,
                createDate = coupon.createDate,
                expireDate = coupon.expireDate,
                status = coupon.status,
                isExpired = coupon.isExpired(),
                isAvailable = coupon.isAvailable()
            )
        }

        /**
         * Coupon 리스트를 CouponResponse 리스트로 변환
         */
        fun fromList(coupons: List<Coupon>): List<CouponResponse> {
            return coupons.map { from(it) }
        }
    }
}
