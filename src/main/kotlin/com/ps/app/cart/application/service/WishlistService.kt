package com.ps.app.cart.application.service

import com.ps.app.cart.adapter.`in`.web.dto.WishlistResponse
import com.ps.app.cart.adapter.out.persistence.WishlistMapper
import com.ps.app.cart.application.port.out.WishlistPort
import com.ps.app.cart.domain.Wishlist
import com.ps.app.cart.domain.WishlistId
import com.ps.app.products.application.port.out.ProductPort
import com.ps.app.products.domain.ProductId
import com.ps.app.user.application.port.out.UserPort
import com.ps.app.user.domain.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class WishlistService(
    private val wishlistPort: WishlistPort,
    private val userPort: UserPort,
    private val productPort: ProductPort
) {

    @Transactional
    fun addToWishlist(userId: Long, productId: Long): WishlistResponse {
        val userIdVO = UserId(userId)
        val productIdVO = ProductId(productId)

        // 중복 체크
        if (wishlistPort.existsByUserIdAndProductId(userIdVO, productIdVO)) {
            throw IllegalStateException("이미 위시리스트에 추가된 상품입니다")
        }

        val user = userPort.findById(userIdVO)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다: $userId")

        val product = productPort.findById(productIdVO)
            ?: throw IllegalArgumentException("상품을 찾을 수 없습니다: $productId")

        val wishlist = Wishlist.create(user, product)
        val saved = wishlistPort.save(wishlist)

        return WishlistMapper.toResponse(saved)
    }

    fun getWishlistByUser(userId: Long): List<WishlistResponse> {
        val wishlists = wishlistPort.findByUserId(UserId(userId))
        return WishlistMapper.toResponseList(wishlists)
    }

    @Transactional
    fun removeFromWishlist(wishlistId: Long) {
        wishlistPort.deleteById(WishlistId(wishlistId))
    }

    @Transactional
    fun removeProductFromWishlist(userId: Long, productId: Long) {
        val wishlist = wishlistPort.findByUserIdAndProductId(
            UserId(userId),
            ProductId(productId)
        ) ?: throw IllegalArgumentException("위시리스트 항목을 찾을 수 없습니다")

        wishlistPort.delete(wishlist)
    }

    fun isInWishlist(userId: Long, productId: Long): Boolean {
        return wishlistPort.existsByUserIdAndProductId(
            UserId(userId),
            ProductId(productId)
        )
    }
}
