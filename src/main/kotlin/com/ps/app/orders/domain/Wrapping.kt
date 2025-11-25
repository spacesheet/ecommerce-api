package com.ps.app.orders.domain

data class Wrapping(
    val id: Int = 0,
    val paper: String,
    val price: Int,
    val deleted: Boolean = false
) {
    fun delete(): Wrapping {
        return copy(deleted = true)
    }

    fun isActive(): Boolean = !deleted

    companion object {
        fun create(paper: String, price: Int): Wrapping {
            return Wrapping(
                paper = paper,
                price = price,
                deleted = false
            )
        }
    }
}
