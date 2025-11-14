package com.ps.app.user.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : JpaRepository<UserJpaEntity, Long> {
    fun findByLoginId(loginId: String): UserJpaEntity?
    fun existsByLoginId(loginId: String): Boolean
}
