package com.ps.app.coupon.application.usecases

import com.ps.app.coupon.application.port.`in`.DeleteCouponPolicyCommand

interface DeleteCouponPolicyUseCase {
    fun deleteCouponPolicy(command: DeleteCouponPolicyCommand)
}
