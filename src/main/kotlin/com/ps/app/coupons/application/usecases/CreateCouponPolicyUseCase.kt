package com.ps.app.coupons.application.usecases

import com.ps.app.coupons.application.port.`in`.CreateCouponPolicyCommand
import com.ps.app.coupons.domain.CouponPolicy

interface CreateCouponPolicyUseCase {
    fun createCouponPolicy(command: CreateCouponPolicyCommand): CouponPolicy
}
