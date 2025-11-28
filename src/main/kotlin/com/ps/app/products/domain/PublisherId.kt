package com.ps.app.products.domain

@JvmInline
value class PublisherId(val value: Long) {
    companion object {
        val NEW = PublisherId(0L)
    }

    fun isNew(): Boolean = value == 0L
}
