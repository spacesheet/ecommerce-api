package com.ps.app.user.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAuthJpaRepository : JpaRepository<UserAuthEntity, Long> {
    fun findByProviderAndProvideId(provider: String, provideId: ByteArray): UserAuthEntity?
    fun findByUserId(userId: Long): List<UserAuthEntity>
}
