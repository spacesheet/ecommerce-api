package com.ps.app.products.domain

data class ProductTag(
    val id: ProductTagId,
    val product: Product,  // 또는 productId: ProductId
    val tag: Tag
) {
    companion object {
        fun create(product: Product, tag: Tag): ProductTag {
            return ProductTag(
                id = ProductTagId.NEW,
                product = product,
                tag = tag
            )
        }
    }
}
