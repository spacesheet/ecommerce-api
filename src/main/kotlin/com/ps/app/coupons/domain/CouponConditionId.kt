package com.ps.app.coupons.domain

@JvmInline
value class CouponConditionId(val value: Long) {
    companion object {
        val NEW = CouponConditionId(-1L)
    }

    fun isNew(): Boolean = this == NEW
}
