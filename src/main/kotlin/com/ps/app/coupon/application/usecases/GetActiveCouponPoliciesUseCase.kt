package com.ps.app.coupon.application.usecases

import com.ps.app.coupon.domain.CouponPolicy

interface GetActiveCouponPoliciesUseCase {
    fun getActivePolicies(): List<CouponPolicy>
}
