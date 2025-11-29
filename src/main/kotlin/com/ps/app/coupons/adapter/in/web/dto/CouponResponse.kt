package com.ps.app.coupons.adapter.`in`.web.dto

import com.ps.app.coupons.domain.Coupons
import com.ps.app.coupons.domain.constant.CouponStatus
import java.time.LocalDate

/**
 * 쿠폰 응답 DTO
 * Primary Adapter의 응답 객체
 */
data class CouponResponse(
    val id: Long?,
    val ownerId: Long,
    val couponPolicyId: Int,
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
        fun from(coupons: Coupons): CouponResponse {
            return CouponResponse(
                id = coupons.id.value,
                ownerId = coupons.ownerId.value,
                couponPolicyId = coupons.couponPolicy.id.value,
                couponCode = coupons.couponCode,
                createDate = coupons.createDate,
                expireDate = coupons.expireDate,
                status = coupons.couponStatus,
                isExpired = coupons.isExpired(),
                isAvailable = coupons.isAvailable()
            )
        }

        /**
         * Coupon 리스트를 CouponResponse 리스트로 변환
         */
        fun fromList(coupons: List<Coupons>): List<CouponResponse> {
            return coupons.map { from(it) }
        }
    }
}
