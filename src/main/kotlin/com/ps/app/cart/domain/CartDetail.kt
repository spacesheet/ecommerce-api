package com.ps.app.cart.domain

import com.ps.app.products.domain.Product


data class CartDetail(
    val id: Long = 0,
    val cart: Cart,
    val product: Product,
    val quantity: Int
) {
    init {
        require(quantity >= 1) { "수량은 1 이상이어야 합니다" }
    }

    fun changeQuantity(newQuantity: Int): CartDetail {
        require(newQuantity >= 1) { "수량은 1 이상이어야 합니다" }
        return copy(quantity = newQuantity)
    }

    fun canWrap(): Boolean {
        return product.hasTag("포장가능")
    }
}
