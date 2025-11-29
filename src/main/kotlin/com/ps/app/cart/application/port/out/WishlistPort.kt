package com.ps.app.cart.application.port.out

import com.ps.app.cart.domain.Wishlist
import com.ps.app.cart.domain.WishlistId
import com.ps.app.products.domain.ProductId
import com.ps.app.user.domain.UserId

interface WishlistPort {
    fun save(wishlist: Wishlist): Wishlist
    fun findById(id: WishlistId): Wishlist?
    fun findByUserId(userId: UserId): List<Wishlist>
    fun findByUserIdAndProductId(userId: UserId, productId: ProductId): Wishlist?
    fun existsByUserIdAndProductId(userId: UserId, productId: ProductId): Boolean
    fun delete(wishlist: Wishlist)
    fun deleteById(id: WishlistId)
}
