package com.ps.app.coupon.application.usecases

import com.ps.app.coupon.application.port.`in`.UseCouponCommand
import com.ps.app.coupon.domain.Coupon

interface UseCouponUseCase {
    fun useCoupon(command: UseCouponCommand): Coupon
}
