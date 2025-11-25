package com.ps.app.coupons.application.usecases

import com.ps.app.coupons.application.port.`in`.GetUserCouponsQuery
import com.ps.app.coupons.domain.Coupons

interface GetUserCouponsUseCase {
    fun getUserCoupons(query: GetUserCouponsQuery): List<Coupons>
}
