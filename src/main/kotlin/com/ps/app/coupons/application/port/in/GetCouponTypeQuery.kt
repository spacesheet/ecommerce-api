package com.ps.app.coupons.application.port.`in`

import com.ps.app.coupons.domain.constant.CouponScope

data class GetCouponTypeQuery(
    val id: Int? = null,
    val scope: CouponScope? = null
)
