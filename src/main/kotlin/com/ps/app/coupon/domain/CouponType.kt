package com.ps.app.coupon.domain

import com.ps.app.common.constant.CouponScope
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

/**
 * 쿠폰 타입 엔티티 클래스입니다.
 *
 * 이 클래스는 쿠폰 타입의 ID와 이름을 포함합니다.
 */
@Entity
class CouponType(
    /**
     * 쿠폰 타입의 ID 입니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    /**
     * 쿠폰 타입의 이름입니다.
     *
     * 이름은 [CouponScope] enum 타입이며, null 일 수 없습니다.
     */
    @NotNull
    @Column(length = 10)
    @Enumerated(value = EnumType.STRING)
    var name: CouponScope
)
