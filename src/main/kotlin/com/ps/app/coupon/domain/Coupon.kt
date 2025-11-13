package com.ps.app.coupon.domain

import com.ps.app.common.constant.CouponStatus
import com.ps.app.user.domain.User
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

/**
 * 쿠폰 엔티티 클래스입니다.
 *
 * 이 클래스는 쿠폰의 ID, 쿠폰 정책, 쿠폰 코드, 생성일, 만료일, 상태를 포함합니다.
 */
@Entity
class Coupon(
    /**
     * 쿠폰의 ID입니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    /**
     * 소유주를 나타내는 User 객체입니다.
     *
     * 유저 객체는 LAZY 로딩 전략을 사용하여 필요할 때 로드됩니다.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    var owner: User,

    /**
     * 쿠폰 정책을 나타내는 CouponPolicy 객체입니다.
     *
     * 쿠폰 정책은 LAZY 로딩 전략을 사용하여 필요할 때 로드됩니다.
     */
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_policy_id")
    var couponPolicy: CouponPolicy,

    /**
     * 쿠폰 코드입니다.
     */
    @NotNull
    @Column(unique = true, length = 20)
    var couponCode: String,

    /**
     * 쿠폰의 생성일입니다.
     */
    @NotNull
    var createDate: LocalDate,

    /**
     * 쿠폰의 만료일입니다.
     */
    @NotNull
    var expireDate: LocalDate,

    /**
     * 쿠폰의 상태입니다.
     */
    @NotNull
    @Enumerated(value = EnumType.STRING)
    var status: CouponStatus
) {
    /**
     * 쿠폰의 상태를 변경합니다.
     *
     * @param newStatus 새로운 쿠폰 상태
     */
    fun changeStatus(newStatus: CouponStatus) {
        this.status = newStatus
    }
}
