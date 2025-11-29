package com.ps.app.cart.domain

import com.ps.app.products.domain.Product
import com.ps.app.products.domain.ProductId
import com.ps.app.user.domain.User
import com.ps.app.user.domain.UserId


data class Wishlist(
    val id: WishlistId = WishlistId.NEW,
    val user: User,
    val product: Product
) {
    companion object {
        fun create(user: User, product: Product): Wishlist {
            return Wishlist(
                id = WishlistId.NEW,
                user = user,
                product = product
            )
        }
    }

    fun isNew(): Boolean = id.isNew()

    fun isSameProduct(productId: ProductId): Boolean {
        return product.id == productId
    }

    fun belongsToUser(userId: UserId): Boolean {
        return user.id == userId
    }
}
