package com.ps.app.products.domain

@JvmInline
value class ProductTagId(val value: Long) {
    companion object {
        val NEW = ProductTagId(0L)
    }

    fun isNew(): Boolean = value == 0L
}
