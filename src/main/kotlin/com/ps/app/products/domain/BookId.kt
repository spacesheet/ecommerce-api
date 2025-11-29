package com.ps.app.products.domain

@JvmInline
value class BookId(val value: Long) {
    companion object {
        val NEW = BookId(0L)
    }

    fun isNew(): Boolean = value == 0L
}
