package com.ps.app.coupon.application.usecases

import com.ps.app.coupon.application.port.`in`.UpdateEndDateCommand
import com.ps.app.coupon.domain.CouponPolicy

interface UpdateCouponPolicyEndDateUseCase {
    fun updateEndDate(command: UpdateEndDateCommand): CouponPolicy
}
