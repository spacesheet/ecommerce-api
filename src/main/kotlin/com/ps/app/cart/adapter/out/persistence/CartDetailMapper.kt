package com.ps.app.cart.adapter.out.persistence

import com.ps.app.cart.adapter.`in`.web.dto.CartDetailResponse
import com.ps.app.cart.domain.CartDetail
import com.ps.app.products.adapter.out.persistence.ProductEntity
import com.ps.app.products.domain.Product



object CartDetailMapper {
    fun toResponse(cartDetail: CartDetail): CartDetailResponse {
        return CartDetailResponse(
            id = cartDetail.id,
            productId = cartDetail.product.id.value,
            productName = cartDetail.product.name,
            quantity = cartDetail.quantity,
            price = cartDetail.product.price.value,
            canWrap = cartDetail.canWrap(),
            thumbnailPath = cartDetail.product.thumbnailPath,
            categoryId = cartDetail.product.category.id
        )
    }

    fun toDomain(entity: CartDetailEntity): CartDetail {
        return CartDetail(
            id = entity.id,
            cart = CartMapper.toDomain(entity.cart),
            product = ProductMapper.toDomain(entity.product),
            quantity = entity.quantity
        )
    }

    fun toEntity(
        domain: CartDetail,
        cartEntity: CartEntity,
        productEntity: ProductEntity
    ): CartDetailEntity {
        return CartDetailEntity(
            id = domain.id,
            cart = cartEntity,
            product = productEntity,
            quantity = domain.quantity
        )
    }
}
