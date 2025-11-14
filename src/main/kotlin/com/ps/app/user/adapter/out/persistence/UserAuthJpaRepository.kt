package com.ps.app.user.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface UserAuthJpaRepository : JpaRepository<UserAuthJpaEntity, Long> {
    fun findByProviderAndProvideId(provider: String, provideId: ByteArray): UserAuthJpaEntity?
    fun findByUserId(userId: Long): List<UserAuthJpaEntity>
}
