package com.ps.app.products.domain

data class ProductTag(
    val id: ProductTagId,
    val productId: ProductId,
    val tagId: TagId
) {
    companion object {
        fun create(productId: ProductId, tagId: TagId): ProductTag {
            return ProductTag(
                id = ProductTagId.NEW,
                productId = productId,
                tagId = tagId
            )
        }
    }

    fun isNew(): Boolean = id.isNew()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ProductTag) return false
        if (id.isNew() || other.id.isNew()) return this === other
        return id == other.id
    }

    override fun hashCode(): Int {
        return if (id.isNew()) System.identityHashCode(this)
        else id.hashCode()
    }
}
