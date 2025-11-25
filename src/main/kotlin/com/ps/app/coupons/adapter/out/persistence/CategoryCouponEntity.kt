package com.ps.app.coupons.adapter.out.persistence

import jakarta.persistence.*
import jakarta.validation.constraints.Min

@Entity
@Table(
    name = "category_coupon",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_coupon_policy_category",
            columnNames = ["coupon_policy_id", "category_id"]
        )
    ],
    indexes = [
        Index(name = "idx_coupon_policy_id", columnList = "coupon_policy_id"),
        Index(name = "idx_category_id", columnList = "category_id")
    ]
)
open class CategoryCouponEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_policy_id", nullable = false)
    val couponPolicy: CouponPolicyEntity,

    @Min(1)
    @Column(name = "category_id", nullable = false)
    val categoryId: Int
) {
    protected constructor() : this(
        null,
        CouponPolicyEntity(),
        0
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CategoryCouponEntity) return false
        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "CategoryCouponEntity(id=$id, categoryId=$categoryId)"
    }
}
