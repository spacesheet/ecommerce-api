package com.ps.app.user.domain

import com.ps.app.user.application.port.`in`.UpdateUserCommand
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "`user`")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @NotNull
    @Size(min = 6, max = 50)
    @Column(name = "login_id", unique = true)
    var loginId: String,

    @NotNull
    @Size(min = 6, max = 15)
    @Column(name = "contact_number")
    var contactNumber: String,

    @NotNull
    @Size(max = 20)
    var name: String,

    @NotNull
    @Size(max = 255)
    @Email
    var email: String,

    @NotNull
    @Size(max = 255)
    var password: String,

    @NotNull
    var birthday: LocalDate,

    @NotNull
    @Column(name = "created_at")
    var createAt: LocalDateTime,

    @Column(name = "last_login_at")
    var lastLoginAt: LocalDateTime? = null,

    @NotNull
    @Enumerated(value = EnumType.STRING)
    var status: UserStatus,

    @NotNull
    @Column(name = "modify_at")
    @Past
    var modifyAt: LocalDateTime,

    @NotNull
    @Column(name = "is_admin")
    @ColumnDefault("false")
    var isAdmin: Boolean = false
) {
    @OneToMany(mappedBy = "user")
    var userCoupons: MutableList<UserCoupon> = mutableListOf()

    fun deactivate() {
        this.status = UserStatus.WITHDRAW
    }

    fun activate() {
        this.status = UserStatus.ACTIVE
    }

    fun updateLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now()
    }

    fun toUserInfo(grade: Grade, point: Int): UserInfo {
        return UserInfo(
            id = this.id,
            name = this.name,
            loginId = this.loginId,
            birthday = this.birthday,
            isAdmin = this.isAdmin,
            grade = grade.toResponse(),
            contactNumber = this.contactNumber,
            email = this.email,
            point = point
        )
    }

    fun updateUserBy(request: UpdateUserCommand) {
        this.contactNumber = request.contactNumber
        this.name = request.name
        this.email = request.email
    }

    fun changePassword(password: String) {
        this.password = password
    }
}
