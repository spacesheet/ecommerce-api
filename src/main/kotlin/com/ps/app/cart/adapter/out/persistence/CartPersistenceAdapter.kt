package com.ps.app.cart.adapter.out.persistence

import com.ps.app.cart.application.port.out.CartDetailPort
import com.ps.app.cart.application.port.out.CartPort
import com.ps.app.cart.domain.Cart
import com.ps.app.cart.domain.CartDetail
import com.ps.app.user.adapter.out.persistence.UserEntity
import com.ps.app.user.adapter.out.persistence.UserJpaRepository
import org.springframework.stereotype.Component
import java.nio.ByteBuffer
import java.util.UUID

@Component
class CartPersistenceAdapter(
    private val cartJpaRepository: CartJpaRepository,
    private val cartDetailJpaRepository: CartDetailJpaRepository,
    private val userJpaRepository: UserJpaRepository
) : CartPort, CartDetailPort {

    override fun findCartById(id: Long): Cart? {
        return cartJpaRepository.findById(id)
            .map { CartMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByUuid(uuid: UUID): Cart? {
        val bytes = uuidToBytes(uuid)
        return cartJpaRepository.findByUuid(bytes)
            ?.let { CartMapper.toDomain(it) }
    }

    override fun findByUserId(userId: Long): Cart? {
        return cartJpaRepository.findByUserId(userId)
            ?.let { CartMapper.toDomain(it) }
    }

    override fun save(cart: Cart): Cart {
        val userEntity = cart.user?.let { findUserEntity(it.id?.value) }
        val entity = CartMapper.toEntity(cart, userEntity)
        val saved = cartJpaRepository.save(entity)
        return CartMapper.toDomain(saved)
    }

    override fun findCartDetailById(id: Long): CartDetail? {
        return cartDetailJpaRepository.findById(id)
            .map { CartDetailMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByCartId(cartId: Long): List<CartDetail> {
        return cartDetailJpaRepository.findByCartId(cartId)
            .map { CartDetailMapper.toDomain(it) }
    }

    override fun save(cartDetail: CartDetail): CartDetail {
        val entity = cartDetailJpaRepository.findById(cartDetail.id)
            .orElseThrow { IllegalArgumentException("CartDetail not found") }

        entity.quantity = cartDetail.quantity

        val saved = cartDetailJpaRepository.save(entity)
        return CartDetailMapper.toDomain(saved)
    }

    override fun delete(cartDetail: CartDetail) {
        cartDetailJpaRepository.deleteById(cartDetail.id)
    }

    // UUID -> ByteArray
    private fun uuidToBytes(uuid: UUID): ByteArray {
        val buffer = ByteBuffer.allocate(16)
        buffer.putLong(uuid.mostSignificantBits)
        buffer.putLong(uuid.leastSignificantBits)
        return buffer.array()
    }

    // ByteArray -> UUID
    private fun bytesToUuid(bytes: ByteArray): UUID {
        require(bytes.size == 16) { "UUID는 16바이트여야 합니다" }
        val buffer = ByteBuffer.wrap(bytes)
        val mostSigBits = buffer.long
        val leastSigBits = buffer.long
        return UUID(mostSigBits, leastSigBits)
    }

    private fun findUserEntity(userId: Long?): UserEntity {
        // User entity lookup logic
        return userJpaRepository.getReferenceById(userId)
    }
}
