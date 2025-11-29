package com.ps.app.products.domain

@JvmInline
value class CategoryId(val value: Int) {
    companion object {
        val NEW = CategoryId(0)
    }

    fun isNew(): Boolean = value == 0
}
