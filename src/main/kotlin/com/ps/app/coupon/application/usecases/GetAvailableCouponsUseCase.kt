package com.ps.app.coupon.application.usecases

import com.ps.app.coupon.application.port.`in`.GetAvailableCouponsQuery
import com.ps.app.coupon.domain.Coupon

interface GetAvailableCouponsUseCase {
    fun getAvailableCoupons(query: GetAvailableCouponsQuery): List<Coupon>
}
