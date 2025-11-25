package com.ps.app.coupons.application.usecases

import com.ps.app.coupons.application.port.`in`.GetCouponByCodeQuery
import com.ps.app.coupons.domain.Coupons

interface GetCouponByCodeUseCase {
    fun getCouponByCode(query: GetCouponByCodeQuery): Coupons
}
