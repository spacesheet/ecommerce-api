package com.ps.app.coupon.domain

import jakarta.persistence.*
import jakarta.validation.constraints.Min

/**
 * 카테고리 쿠폰 엔티티 클래스입니다.
 *
 * 이 클래스는 카테고리 쿠폰의 ID, 쿠폰 정책, 카테고리 ID를 포함합니다.
 */
@Entity
class CategoryCoupon(
    /**
     * 카테고리 쿠폰의 ID 입니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    /**
     * 쿠폰 정책을 나타내는 CouponPolicy 객체입니다.
     *
     * 쿠폰 정책은 LAZY 로딩 전략을 사용하여 필요할 때 로드됩니다.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_policy_id")
    var couponPolicy: CouponPolicy,

    /**
     * 카테고리 ID 입니다.
     *
     * 카테고리 ID는 1 이상이어야 합니다.
     */
    @Min(1)
    var categoryId: Int
)
