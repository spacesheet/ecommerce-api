package com.ps.app.products.domain

@JvmInline
value class ProductId(val value: Long) {
    companion object {
        val NEW = ProductId(0L)
    }

    fun isNew(): Boolean = value == 0L
}
