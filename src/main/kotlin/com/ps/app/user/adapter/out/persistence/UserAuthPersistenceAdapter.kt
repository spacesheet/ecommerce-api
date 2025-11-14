package com.ps.app.user.adapter.out.persistence

import com.ps.app.user.application.port.out.*
import com.ps.app.user.domain.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserAuthPersistenceAdapter(
    private val userAuthJpaRepository: UserAuthJpaRepository
) : LoadUserAuthPort, SaveUserAuthPort, DeleteUserAuthPort {

    override fun findById(id: UserAuthId): UserAuth? {
        return userAuthJpaRepository.findById(id.value)
            .map { UserAuthPersistenceMapper.mapToDomain(it) }
            .orElse(null)
    }

    override fun findByProviderAndProvideId(
        provider: AuthProvider,
        provideId: ProvideId
    ): UserAuth? {
        val provideIdBytes = UUID.fromString(provideId.value).toString().toByteArray()
        return userAuthJpaRepository.findByProviderAndProvideId(provider.value, provideIdBytes)
            ?.let { UserAuthPersistenceMapper.mapToDomain(it) }
    }

    override fun findByUserId(userId: UserId): List<UserAuth> {
        return userAuthJpaRepository.findByUserId(userId.value)
            .map { UserAuthPersistenceMapper.mapToDomain(it) }
    }

    override fun save(userAuth: UserAuth): UserAuth {
        val entity = UserAuthPersistenceMapper.mapToEntity(userAuth)
        val savedEntity = userAuthJpaRepository.save(entity)
        return UserAuthPersistenceMapper.mapToDomain(savedEntity)
    }

    override fun deleteById(id: UserAuthId) {
        userAuthJpaRepository.deleteById(id.value)
    }
}
