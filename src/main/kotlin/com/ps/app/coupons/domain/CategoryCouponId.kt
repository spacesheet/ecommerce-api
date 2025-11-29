package com.ps.app.coupons.domain

@JvmInline
value class CategoryCouponId(val value: Int) {
    companion object {
        val NEW = CategoryCouponId(0)
    }

    fun isNew(): Boolean = value == 0
}
