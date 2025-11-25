package com.ps.app.coupons.adapter.`in`.web.dto

import com.ps.app.coupons.domain.constant.CouponScope

data class CreateCouponTypeRequest(
    val scope: CouponScope
)
