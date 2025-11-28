package com.ps.app.products.domain

data class Publisher(
    val id: PublisherId,
    val name: String
) {
    companion object {
        fun create(name: String): Publisher {
            require(name.isNotBlank()) { "출판사명은 필수입니다" }
            return Publisher(
                id = PublisherId.NEW,
                name = name
            )
        }
    }

    fun isNew(): Boolean = id.isNew()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Publisher) return false
        if (id.isNew() || other.id.isNew()) return this === other
        return id == other.id
    }

    override fun hashCode(): Int {
        return if (id.isNew()) System.identityHashCode(this)
        else id.hashCode()
    }

    override fun toString(): String = "Publisher(id=$id, name='$name')"
}
