package com.ps.app.coupon.application.usecases

import com.ps.app.coupon.application.port.`in`.GetCouponByCodeQuery
import com.ps.app.coupon.domain.Coupon

interface GetCouponByCodeUseCase {
    fun getCouponByCode(query: GetCouponByCodeQuery): Coupon
}
