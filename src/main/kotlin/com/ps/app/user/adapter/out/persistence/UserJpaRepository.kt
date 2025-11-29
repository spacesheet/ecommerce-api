package com.ps.app.user.adapter.out.persistence

import com.ps.app.user.domain.UserId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findById(id: Long?): Optional<UserEntity?>
    fun findByLoginId(loginId: String): UserEntity?
    fun existsByLoginId(loginId: String): Boolean
    fun getReferenceById(id: Long?): com.ps.app.user.adapter.out.persistence.UserEntity
    fun findById(id: UserId): Optional<UserEntity?>
}
