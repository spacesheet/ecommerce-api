package com.ps.app.coupons.application.usecases

import com.ps.app.coupons.application.port.`in`.IssueCouponCommand
import com.ps.app.coupons.domain.Coupons

interface IssueCouponUseCase {
    fun issueCoupon(command: IssueCouponCommand): Coupons
}

