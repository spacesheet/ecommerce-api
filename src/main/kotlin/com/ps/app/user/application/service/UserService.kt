package com.ps.app.user.application.service

import com.ps.app.common.annotation.Loggable
import com.ps.app.user.application.dto.UpdateUserCommand
import com.ps.app.user.application.port.out.UserPort
import com.ps.app.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * User 애플리케이션 서비스
 * 유스케이스를 구현하며, 도메인 모델을 사용하여 비즈니스 로직 수행
 */
@Service
@Transactional
class UserService(
    private val userPort: UserPort
) {

    /**
     * 사용자 정보를 업데이트합니다.
     */
    @Loggable(includeArgs = true, includeResult = true)
    fun updateUser(userId: Long, command: UpdateUserCommand): User {
        val user = userPort.findById(userId)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다.")

        // 도메인 모델의 비즈니스 로직 사용
        user.updateInfo(
            contactNumber = command.contactNumber,
            name = command.name,
            email = command.email
        )

        return userPort.save(user)
    }

    /**
     * 사용자를 비활성화합니다.
     */
    fun deactivateUser(userId: Long): User {
        val user = userPort.findById(userId)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다.")

        user.deactivate()

        return userPort.save(user)
    }

    /**
     * 마지막 로그인 시간을 갱신합니다.
     */
    fun updateLastLogin(userId: Long): User {
        val user = userPort.findById(userId)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다.")

        user.updateLastLoginAt()

        return userPort.save(user)
    }

    /**
     * 비밀번호를 변경합니다.
     */
    fun changePassword(userId: Long, newPassword: String): User {
        val user = userPort.findById(userId)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다.")

        user.changePassword(newPassword)

        return userPort.save(user)
    }
}
