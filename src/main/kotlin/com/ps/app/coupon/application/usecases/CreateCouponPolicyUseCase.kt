package com.ps.app.coupon.application.usecases

import com.ps.app.coupon.application.port.`in`.CreateCouponPolicyCommand
import com.ps.app.coupon.domain.CouponPolicy
import com.ps.app.coupon.domain.constant.DiscountType
import java.time.LocalDate

interface CreateCouponPolicyUseCase {
    fun createCouponPolicy(command: CreateCouponPolicyCommand): CouponPolicy
}
