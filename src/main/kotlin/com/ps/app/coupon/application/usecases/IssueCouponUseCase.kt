package com.ps.app.coupon.application.usecases

import com.ps.app.coupon.application.port.`in`.IssueCouponCommand
import com.ps.app.coupon.domain.Coupon

interface IssueCouponUseCase {
    fun issueCoupon(command: IssueCouponCommand): Coupon
}

