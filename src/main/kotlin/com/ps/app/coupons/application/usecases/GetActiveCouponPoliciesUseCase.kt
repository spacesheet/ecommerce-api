package com.ps.app.coupons.application.usecases

import com.ps.app.coupons.domain.CouponPolicy

interface GetActiveCouponPoliciesUseCase {
    fun getActivePolicies(): List<CouponPolicy>
}
