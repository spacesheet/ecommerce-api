package com.ps.app.user.adapter.out.persistence

import com.ps.app.user.domain.User
import org.springframework.stereotype.Component

/**
 * User 도메인 모델과 JPA 엔티티 간 변환을 담당하는 매퍼
 */
@Component
class UserMapper {

    /**
     * 도메인 모델을 JPA 엔티티로 변환
     */
    fun toEntity(user: User): UserJpaEntity {
        return UserJpaEntity(
            id = user.id,
            loginId = user.loginId,
            contactNumber = user.contactNumber,
            name = user.name,
            email = user.email,
            password = user.password,
            birthday = user.birthday,
            createAt = user.createAt,
            lastLoginAt = user.lastLoginAt,
            status = user.status,
            modifyAt = user.modifyAt,
            isAdmin = user.isAdmin
        )
    }

    /**
     * JPA 엔티티를 도메인 모델로 변환
     */
    fun toDomain(entity: UserJpaEntity): User {
        return User(
            id = entity.id,
            loginId = entity.loginId,
            contactNumber = entity.contactNumber,
            name = entity.name,
            email = entity.email,
            password = entity.password,
            birthday = entity.birthday,
            createAt = entity.createAt,
            lastLoginAt = entity.lastLoginAt,
            status = entity.status,
            modifyAt = entity.modifyAt,
            isAdmin = entity.isAdmin
        )
    }

    /**
     * 도메인 모델 리스트를 엔티티 리스트로 변환
     */
    fun toEntityList(users: List<User>): List<UserJpaEntity> {
        return users.map { toEntity(it) }
    }

    /**
     * 엔티티 리스트를 도메인 모델 리스트로 변환
     */
    fun toDomainList(entities: List<UserJpaEntity>): List<User> {
        return entities.map { toDomain(it) }
    }
}
