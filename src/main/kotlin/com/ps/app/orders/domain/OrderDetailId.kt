package com.ps.app.orders.domain

@JvmInline
value class OrderDetailId(val value: Long) {
    companion object {
        val NEW = OrderDetailId(0L)
    }

    fun isNew(): Boolean = value == 0L
}

