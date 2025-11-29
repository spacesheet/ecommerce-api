package com.ps.app.coupons.adapter.out.persistence

import jakarta.persistence.*

@Entity
@Table(
    name = "product_coupon",
    indexes = [
        Index(name = "idx_product_coupon_policy_id", columnList = "coupon_policy_id"),
        Index(name = "idx_product_coupon_product_id", columnList = "product_id")
    ],
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_product_coupon",
            columnNames = ["coupon_policy_id", "product_id"]
        )
    ]
)
class ProductCouponEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_policy_id", nullable = false)
    val couponPolicy: CouponPolicyEntity,

    @Column(name = "product_id", nullable = false)
    val productId: Long
)
