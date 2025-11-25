package com.ps.app.coupons.application.usecases

import com.ps.app.coupons.application.port.`in`.GetAvailableCouponsQuery
import com.ps.app.coupons.domain.Coupons

interface GetAvailableCouponsUseCase {
    fun getAvailableCoupons(query: GetAvailableCouponsQuery): List<Coupons>
}
