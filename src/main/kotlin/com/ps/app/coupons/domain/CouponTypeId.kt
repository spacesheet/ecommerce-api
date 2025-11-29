package com.ps.app.coupons.domain

@JvmInline
value class CouponTypeId(val value: Int) {
    companion object {
        val NEW = CouponTypeId(0)
    }

    fun isNew(): Boolean = value == 0
}
