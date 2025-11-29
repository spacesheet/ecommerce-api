package com.ps.app.coupons.domain

@JvmInline
value class CouponPolicyId(val value: Int) {
    companion object {
        val NEW = CouponPolicyId(0)
    }

    fun isNew(): Boolean = value == 0
}
