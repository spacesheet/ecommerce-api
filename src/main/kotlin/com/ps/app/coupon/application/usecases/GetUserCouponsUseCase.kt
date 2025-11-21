package com.ps.app.coupon.application.usecases

import com.ps.app.coupon.application.port.`in`.GetUserCouponsQuery
import com.ps.app.coupon.domain.Coupon

interface GetUserCouponsUseCase {
    fun getUserCoupons(query: GetUserCouponsQuery): List<Coupon>
}
