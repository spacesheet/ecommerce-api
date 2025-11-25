package com.ps.app.coupons.adapter.`in`.web.dto

import com.ps.app.coupons.domain.CouponType
import com.ps.app.coupons.domain.constant.CouponScope

data class CouponTypeResponse(
    val id: Int?,
    val name: CouponScope
) {
    companion object {
        fun from(couponType: CouponType): CouponTypeResponse {
            return CouponTypeResponse(
                id = couponType.id,
                name = couponType.name
            )
        }
    }
}
