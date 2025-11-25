package com.ps.app.coupons.adapter.`in`.web.dto

import com.ps.app.coupons.domain.Coupons
import com.ps.app.coupons.domain.CouponPolicy
import com.ps.app.coupons.domain.constant.CouponStatus
import com.ps.app.coupons.domain.constant.DiscountType
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
        fun from(coupons: Coupons, policy: CouponPolicy): CouponDetailResponse {
            return CouponDetailResponse(
                id = coupons.id,
                ownerId = coupons.ownerId,
                couponCode = coupons.couponCode,
                createDate = coupons.createDate,
                expireDate = coupons.expireDate,
                status = coupons.status,
                isExpired = coupons.isExpired(),
                isAvailable = coupons.isAvailable(),
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
