package com.ps.app.coupons.domain

@JvmInline
value class CouponPolicyId(val value: Long) {
    companion object {
        val NEW = CouponPolicyId(-1L)
        val NONE = CouponPolicyId(0L)
    }

    fun isNew(): Boolean = this == NEW
    fun isNone(): Boolean = this == NONE
}
