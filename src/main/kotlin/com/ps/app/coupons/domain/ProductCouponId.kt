package com.ps.app.coupons.domain

@JvmInline
value class ProductCouponId(val value: Int) {
    companion object {
        val NEW = ProductCouponId(0)
    }

    fun isNew(): Boolean = value == 0
}
