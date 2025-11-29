package com.ps.app.cart.adapter.out.persistence

import com.ps.app.cart.domain.Cart
import com.ps.app.user.adapter.out.persistence.UserEntity
import com.ps.app.user.adapter.out.persistence.UserMapper
import java.nio.ByteBuffer
import java.util.UUID

object CartMapper {
    fun toDomain(entity: CartEntity): Cart {
        return Cart(
            id = entity.id,
            user = entity.user?.let { UserMapper.toDomain(it) },
            uuid = uuidFromBytes(entity.uuid)
        )
    }

    fun toEntity(domain: Cart, userEntity: UserEntity?): CartEntity {
        return CartEntity(
            id = domain.id,
            user = userEntity,
            uuid = uuidToBytes(domain.uuid)
        )
    }

    private fun uuidFromBytes(bytes: ByteArray): UUID {
        val buffer = ByteBuffer.wrap(bytes)
        return UUID(buffer.long, buffer.long)
    }

    private fun uuidToBytes(uuid: UUID): ByteArray {
        val buffer = ByteBuffer.allocate(16)
        buffer.putLong(uuid.mostSignificantBits)
        buffer.putLong(uuid.leastSignificantBits)
        return buffer.array()
    }
}
