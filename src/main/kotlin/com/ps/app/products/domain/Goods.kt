package com.ps.app.products.domain

data class Goods(
    val id: GoodsId,
    val productId: ProductId?,
    val name: String,
    val manufacturer: String,
    val category: String
) {
    fun isRegisteredAsProduct(): Boolean = productId != null
}
