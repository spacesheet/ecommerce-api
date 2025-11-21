package com.ps.app.coupon.adapter.`in`.web.dto

import com.ps.app.coupon.domain.Coupon
import com.ps.app.coupon.domain.CouponPolicy
import com.ps.app.coupon.domain.constant.CouponStatus
import com.ps.app.coupon.domain.constant.DiscountType
import java.time.LocalDate

/**
 * 쿠폰 상세 정보 응답 DTO
 * 쿠폰 정책 정보를 포함
 */
data class CouponDetailResponse(
    val id: Long?,
    val ownerId: Long,
    val couponCode: String,
    val createDate: LocalDate,
    val expireDate: LocalDate,
    val status: CouponStatus,
    val isExpired: Boolean,
    val isAvailable: Boolean,
    val policy: CouponPolicyInfo
) {
    data class CouponPolicyInfo(
        val id: Int?,
        val name: String,
        val discountType: DiscountType,
        val discountRate: Double,
        val discountAmount: Int,
        val standardPrice: Int,
        val maxDiscountAmount: Int
    )

    companion object {
        fun from(coupon: Coupon, policy: CouponPolicy): CouponDetailResponse {
            return CouponDetailResponse(
                id = coupon.id,
                ownerId = coupon.ownerId,
                couponCode = coupon.couponCode,
                createDate = coupon.createDate,
                expireDate = coupon.expireDate,
                status = coupon.status,
                isExpired = coupon.isExpired(),
                isAvailable = coupon.isAvailable(),
                policy = CouponPolicyInfo(
                    id = policy.id,
                    name = policy.name,
                    discountType = policy.discountType,
                    discountRate = policy.discountRate,
                    discountAmount = policy.discountAmount,
                    standardPrice = policy.standardPrice,
                    maxDiscountAmount = policy.maxDiscountAmount
                )
            )
        }
    }
}
