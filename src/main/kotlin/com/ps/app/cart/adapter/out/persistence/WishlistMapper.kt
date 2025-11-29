package com.ps.app.cart.adapter.out.persistence

import com.ps.app.cart.adapter.`in`.web.dto.WishlistResponse
import com.ps.app.cart.domain.Wishlist
import com.ps.app.cart.domain.WishlistId
import com.ps.app.products.adapter.out.persistence.ProductEntity
import com.ps.app.products.adapter.out.persistence.ProductMapper
import com.ps.app.user.adapter.out.persistence.UserEntity
import com.ps.app.user.adapter.out.persistence.UserMapper

object WishlistMapper {
    fun toDomain(entity: WishlistEntity): Wishlist {
        return Wishlist(
            id = WishlistId(entity.id),
            user = UserMapper.toDomain(entity.user),
            product = ProductMapper.toDomain(entity.product)
        )
    }

    fun toEntity(
        domain: Wishlist,
        userEntity: UserEntity?,
        productEntity: ProductEntity?
    ): WishlistEntity {
        return WishlistEntity(
            id = domain.id.value,
            user = userEntity,
            product = productEntity
        )
    }

    fun toResponse(wishlist: Wishlist): WishlistResponse {
        return WishlistResponse(
            id = wishlist.id.value,
            userId = wishlist.user.id.value,
            productId = wishlist.product.id.value,
            productName = wishlist.product.productName,
            price = wishlist.product.price.value.toInt(),
            thumbnailPath = wishlist.product.thumbnailPath,
            canWrap = wishlist.product.hasTag("포장가능")
        )
    }

    fun toResponseList(wishlists: List<Wishlist>): List<WishlistResponse> {
        return wishlists.map { toResponse(it) }
    }
}
