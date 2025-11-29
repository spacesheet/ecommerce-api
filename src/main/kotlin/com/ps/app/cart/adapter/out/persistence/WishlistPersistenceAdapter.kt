package com.ps.app.cart.adapter.out.persistence

import com.ps.app.cart.application.port.out.WishlistPort
import com.ps.app.cart.domain.Wishlist
import com.ps.app.cart.domain.WishlistId
import com.ps.app.products.adapter.out.persistence.ProductJpaRepository
import com.ps.app.products.domain.ProductId
import com.ps.app.user.adapter.out.persistence.UserJpaRepository
import com.ps.app.user.domain.UserId
import org.springframework.stereotype.Component


@Component
class WishlistPersistenceAdapter(
    private val wishlistRepository: WishlistJpaRepository,
    private val userRepository: UserJpaRepository,
    private val productRepository: ProductJpaRepository
) : WishlistPort {

    override fun save(wishlist: Wishlist): Wishlist {
        val userEntity = userRepository.findById(wishlist.user.id?.value)
            .orElseThrow { IllegalArgumentException("User not found: ${wishlist.user.id}") }

        val productEntity = productRepository.findById(wishlist.product.id.value)
            .orElseThrow { IllegalArgumentException("Product not found: ${wishlist.product.id}") }

        val entity = if (wishlist.isNew()) {
            WishlistMapper.toEntity(wishlist, userEntity, productEntity)
        } else {
            wishlistRepository.findById(wishlist.id.value)
                .orElseThrow { IllegalArgumentException("Wishlist not found: ${wishlist.id}") }
        }

        val saved = wishlistRepository.save(entity)
        return WishlistMapper.toDomain(saved)
    }

    override fun findById(id: WishlistId): Wishlist? {
        return wishlistRepository.findById(id.value)
            .map { WishlistMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByUserId(userId: UserId): List<Wishlist> {
        return wishlistRepository.findByUserIdWithProduct(userId.value)
            .map { WishlistMapper.toDomain(it) }
    }

    override fun findByUserIdAndProductId(userId: UserId, productId: ProductId): Wishlist? {
        return wishlistRepository.findByUserIdAndProductId(userId.value, productId.value)
            ?.let { WishlistMapper.toDomain(it) }
    }

    override fun existsByUserIdAndProductId(userId: UserId, productId: ProductId): Boolean {
        return wishlistRepository.existsByUserIdAndProductId(userId.value, productId.value)
    }

    override fun delete(wishlist: Wishlist) {
        wishlistRepository.deleteById(wishlist.id.value)
    }

    override fun deleteById(id: WishlistId) {
        wishlistRepository.deleteById(id.value)
    }
}
