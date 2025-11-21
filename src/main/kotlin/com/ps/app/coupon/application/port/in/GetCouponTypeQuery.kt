package com.ps.app.coupon.application.port.`in`

import com.ps.app.coupon.domain.constant.CouponScope

data class GetCouponTypeQuery(
    val id: Int? = null,
    val scope: CouponScope? = null
)
