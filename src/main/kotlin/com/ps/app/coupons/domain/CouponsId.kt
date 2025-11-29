package com.ps.app.coupons.domain

@JvmInline
value class CouponsId(val value: Long) {
    companion object {
        val NEW = CouponsId(0L)
    }

    fun isNew(): Boolean = value == 0L
}
