package com.ps.app.cart.domain

@JvmInline
value class WishlistId(val value: Long) {
    companion object {
        val NEW = WishlistId(0L)
    }

    fun isNew(): Boolean = value == 0L
}
