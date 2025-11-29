package com.ps.app.products.domain

@JvmInline
value class TagId(val value: Int) {
    companion object {
        val NEW = TagId(0)
    }

    fun isNew(): Boolean = value == 0
}
