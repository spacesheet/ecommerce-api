package com.ps.app.coupon.application.port.`in`

import com.ps.app.coupon.domain.constant.CouponStatus

data class GetUserCouponsQuery(
    val userId: Long,
    val status: CouponStatus? = null,
    val includeExpired: Boolean = false
)
