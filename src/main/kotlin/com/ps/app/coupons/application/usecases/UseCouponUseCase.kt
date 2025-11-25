package com.ps.app.coupons.application.usecases

import com.ps.app.coupons.application.port.`in`.UseCouponCommand
import com.ps.app.coupons.domain.Coupons

interface UseCouponUseCase {
    fun useCoupon(command: UseCouponCommand): Coupons
}
