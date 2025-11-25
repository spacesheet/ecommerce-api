package com.ps.app.products.domain

class Publisher(
    val id: Int = 0,
    val name: String
) {
    companion object {
        fun create(name: String): Publisher {
            require(name.isNotBlank()) { "출판사명은 필수입니다" }
            return Publisher(name = name)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Publisher) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = "Publisher(id=$id, name='$name')"
}
