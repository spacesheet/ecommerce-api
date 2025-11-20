package com.ps.app.user.adapter.out.persistence

import com.ps.app.user.domain.*
import java.util.*

object UserAuthPersistenceMapper {

    fun mapToDomain(entity: UserAuthEntity): UserAuth {
        return UserAuth(
            id = entity.id?.let { UserAuthId(it) },
            userId = UserId(entity.userId),
            provider = AuthProvider.from(entity.provider),
            provideId = ProvideId(UUID.nameUUIDFromBytes(entity.provideId).toString())
        )
    }

    fun mapToEntity(domain: UserAuth): UserAuthEntity {
        return UserAuthEntity(
            id = domain.id?.value,
            userId = domain.userId.value,
            provider = domain.provider.value,
            provideId = uuidToBytes(domain.provideId.value)
        )
    }

    private fun uuidToBytes(uuid: String): ByteArray {
        return UUID.fromString(uuid).toString().toByteArray()
    }
}
