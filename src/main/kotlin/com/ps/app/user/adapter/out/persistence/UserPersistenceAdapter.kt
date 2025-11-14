package com.ps.app.user.adapter.out.persistence

import com.ps.app.user.application.port.out.UserRepository
import com.ps.app.user.domain.User
import org.springframework.stereotype.Component

/**
 * User 영속성 어댑터
 * 아웃바운드 포트(UserRepository)를 구현하여 도메인과 영속성 계층을 연결
 */
@Component
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository,
    private val userMapper: UserMapper
) : UserRepository {

    override fun save(user: User): User {
        val entity = userMapper.toEntity(user)
        val savedEntity = userJpaRepository.save(entity)
        return userMapper.toDomain(savedEntity)
    }

    override fun findById(id: Long): User? {
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
