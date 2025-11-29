package com.ps.app.orders.domain

@JvmInline
value class OrderId(val value: Long) {
    companion object {
        val NEW = OrderId(0L)
    }

    fun isNew(): Boolean = value == 0L
}
