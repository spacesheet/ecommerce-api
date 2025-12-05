package com.ps.app.coupons.domain

@JvmInline
value class CouponConditionId(val value: Long) {
    companion object {
        val NEW = CouponConditionId(-1L)
        val NONE = CouponConditionId(0L)  // ✅ 추가
    }

    fun isNew(): Boolean = this == NEW
    fun isNone(): Boolean = this == NONE
}
