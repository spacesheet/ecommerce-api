package com.ps.app.user.adapter.out.persistence

import com.ps.app.user.application.port.out.UserPort
import com.ps.app.user.domain.User
import com.ps.app.user.domain.UserId
import org.springframework.stereotype.Component

/**
 * User 영속성 어댑터
 * 아웃바운드 포트(UserRepository)를 구현하여 도메인과 영속성 계층을 연결
 */
@Component
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository,
    private val userMapper: UserMapper
) : UserPort {

    override fun save(user: User): User {
        val entity = userMapper.toEntity(user)
        val savedEntity = userJpaRepository.save(entity)
        return userMapper.toDomain(savedEntity)
    }

    override fun findById(id: UserId): User? {
        return userJpaRepository.findById(id)
            .map { userMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByLoginId(loginId: String): User? {
        return userJpaRepository.findByLoginId(loginId)
            ?.let { userMapper.toDomain(it) }
    }

    override fun existsByLoginId(loginId: String): Boolean {
        return userJpaRepository.existsByLoginId(loginId)
    }

    override fun delete(user: User) {
        user.id?.let { userJpaRepository.deleteById(it) }
    }

    override fun findAll(): List<User> {
        return userMapper.toDomainList(userJpaRepository.findAll())
    }
}
