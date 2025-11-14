package com.ps.app.user.application.port.out

import com.ps.app.user.domain.User
import org.springframework.stereotype.Repository

/**
 * User 리포지토리 포트
 * 도메인 계층에서 정의하는 인터페이스
 */
@Repository
interface UserRepository {
    fun save(user: User): User
    fun findById(id: Long): User?
    fun findByLoginId(loginId: String): User?
    fun existsByLoginId(loginId: String): Boolean
    fun delete(user: User)
    fun findAll(): List<User>
}
