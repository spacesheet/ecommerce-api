package com.ps.app.user.adapter.out.persistence

import com.ps.app.user.domain.UserId
import com.ps.app.user.domain.constant.UserStatus
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * User JPA 엔티티
 * 데이터베이스 매핑만 담당하며, 비즈니스 로직은 포함하지 않음
 */
@Entity
@Table(name = "`user`")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: UserId = null,

    @Column(name = "login_id", unique = true, length = 50, nullable = false)
    var loginId: String,

    @Column(name = "contact_number", length = 15, nullable = false)
    var contactNumber: String,

    @Column(length = 20, nullable = false)
    var name: String,

    @Column(length = 255, nullable = false)
    var email: String,

    @Column(length = 255, nullable = false)
    var password: String,

    @Column(nullable = false)
    var birthday: LocalDate,

    @Column(name = "create_at", nullable = false)
    var createAt: LocalDateTime,

    @Column(name = "last_login_at")
    var lastLoginAt: LocalDateTime? = null,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    var status: UserStatus,

    @Column(name = "modify_at", nullable = false)
    var modifyAt: LocalDateTime,

    @Column(name = "is_admin", nullable = false)
    @ColumnDefault("false")
    var isAdmin: Boolean = false
) {
    @OneToMany(mappedBy = "user")
    var userCoupons: MutableList<UserCouponJpaEntity> = mutableListOf()
}
