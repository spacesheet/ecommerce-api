package com.ps.app.coupons.application.port.`in`

import com.ps.app.coupons.domain.constant.CouponStatus

data class GetUserCouponsQuery(
    val userId: Long,
    val status: CouponStatus? = null,
    val includeExpired: Boolean = false
)
