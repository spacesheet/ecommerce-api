package com.ps.app.product.domain

class Author(
    val id: Long = 0,
    val name: String
) {
    companion object {
        fun create(name: String): Author {
            require(name.isNotBlank()) { "저자명은 필수입니다" }
            return Author(name = name)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Author) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = "Author(id=$id, name='$name')"
}
