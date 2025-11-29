// Domain Layer (domain/User.kt)
package com.ps.app.user.domain

import com.ps.app.user.domain.constant.UserStatus
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * User 도메인 모델
 * 순수한 비즈니스 로직만 포함하며, JPA 의존성이 없음
 */
class User(
    val id: UserId? = UserId.NEW,
    var loginId: String,
    var contactNumber: String,
    var name: String,
    var email: String,
    var password: String,
    val birthday: LocalDate,
    val createAt: LocalDateTime,
    var lastLoginAt: LocalDateTime? = null,
    var status: UserStatus,
    var modifyAt: LocalDateTime,
    val isAdmin: Boolean = false
) {
    /**
     * 사용자를 비활성화합니다.
     */
    fun deactivate() {
        this.status = UserStatus.WITHDRAW
        this.modifyAt = LocalDateTime.now()
    }

    /**
     * 사용자를 활성화합니다.
     */
    fun activate() {
        this.status = UserStatus.ACTIVE
        this.modifyAt = LocalDateTime.now()
    }

    /**
     * 마지막 로그인 시간을 갱신합니다.
     */
    fun updateLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now()
    }

    /**
     * 사용자 정보를 업데이트합니다.
     */
    fun updateInfo(contactNumber: String, name: String, email: String) {
        this.contactNumber = contactNumber
        this.name = name
        this.email = email
        this.modifyAt = LocalDateTime.now()
    }

    /**
     * 비밀번호를 변경합니다.
     */
    fun changePassword(newPassword: String) {
        this.password = newPassword
        this.modifyAt = LocalDateTime.now()
    }

    /**
     * 사용자가 활성 상태인지 확인합니다.
     */
    fun isActive(): Boolean = status == UserStatus.ACTIVE

    /**
     * 사용자 정보를 읽기 전용 VO로 변환합니다.
     * Domain 모델끼리만 조합
     */
    fun toInfo(grade: Grade, point: Int): UserInfo {
        return UserInfo(
            userId = this.id?.value,
            name = this.name,
            loginId = this.loginId,
            birthday = this.birthday,
            isAdmin = this.isAdmin,
            grade = grade,  // Grade 도메인 모델 그대로 사용
            contactNumber = this.contactNumber,
            email = this.email,
            point = point
        )
    }

    companion object {
        /**
         * 새로운 사용자를 생성합니다.
         */
        fun create(
            loginId: String,
            contactNumber: String,
            name: String,
            email: String,
            password: String,
            birthday: LocalDate,
            isAdmin: Boolean = false
        ): User {
            return User(
                loginId = loginId,
                contactNumber = contactNumber,
                name = name,
                email = email,
                password = password,
                birthday = birthday,
                createAt = LocalDateTime.now(),
                status = UserStatus.ACTIVE,
                modifyAt = LocalDateTime.now(),
                isAdmin = isAdmin
            )
        }
    }
}
