package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.domain.constant.DiscountType
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(
    name = "coupon_policy",
    indexes = [
        Index(name = "idx_coupon_policy_type_id", columnList = "coupon_type_id"),
        Index(name = "idx_coupon_policy_dates", columnList = "start_date, end_date"),
        Index(name = "idx_coupon_policy_deleted", columnList = "deleted")
    ]
)
class CouponPolicyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_type_id", nullable = false)
    val couponType: CouponTypeEntity,

    @Column(nullable = false, length = 30)
    val name: String,

    @Column(name = "discount_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    val discountType: DiscountType,

    @Column(name = "discount_rate", nullable = false)
    val discountRate: Double,

    @Column(name = "discount_amount", nullable = false)
    val discountAmount: Int,

    @Column(nullable = false)
    val period: Int,

    @Column(name = "standard_price", nullable = false)
    val standardPrice: Int,

    @Column(name = "max_discount_amount", nullable = false)
    val maxDiscountAmount: Int,

    @Column(name = "start_date", nullable = false)
    val startDate: LocalDate,

    @Column(name = "end_date", nullable = false)
    var endDate: LocalDate,

    @Column(nullable = false)
    var deleted: Boolean = false,

    @Column(nullable = false)
    var isActive: Boolean = true
) {
    fun changeEndDate(newEndDate: LocalDate) {
        require(newEndDate >= startDate) { "End date must be after or equal to start date" }
        this.endDate = newEndDate
    }

    fun delete() {
        this.deleted = true
    }

    fun isActive(): Boolean {
        val now = LocalDate.now()
        return !deleted && now >= startDate && now <= endDate
    }
}
