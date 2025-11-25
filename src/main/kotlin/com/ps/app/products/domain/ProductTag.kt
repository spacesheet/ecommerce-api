package com.ps.app.products.domain

data class ProductTag(
    val productId: Int,
    val tagId: Int,
    val product: Product,
    val tag: Tag
) {
    companion object {
        fun create(product: Product, tag: Tag): ProductTag {
            return ProductTag(
                productId = product.id,
                tagId = tag.id,
                product = product,
                tag = tag
            )
        }
    }
}
