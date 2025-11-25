package com.ps.app.coupons.application.usecases

import com.ps.app.coupons.application.port.`in`.DeleteCouponPolicyCommand

interface DeleteCouponPolicyUseCase {
    fun deleteCouponPolicy(command: DeleteCouponPolicyCommand)
}
