package com.ps.app.coupons.application.usecases

import com.ps.app.coupons.application.port.`in`.UpdateEndDateCommand
import com.ps.app.coupons.domain.CouponPolicy

interface UpdateCouponPolicyEndDateUseCase {
    fun updateEndDate(command: UpdateEndDateCommand): CouponPolicy
}
