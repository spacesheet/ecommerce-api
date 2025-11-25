package com.ps.app.coupons.adapter.out.persistence

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDate

@Entity
@Table(name = "coupon_policy")
class CouponPolicyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_type_id")
    val couponType: CouponTypeEntity,

    @NotNull
    @Column(length = 30)
    val name: String,

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", length = 20)
    val discountType: String,

    @NotNull
    @ColumnDefault("0")
    @Column(name = "discount_rate")
    val discountRate: Double,

    @NotNull
    @ColumnDefault("0")
    @Column(name = "discount_amount")
    val discountAmount: Int,

    @NotNull
    @Column(name = "period")
    val period: Int,

    @NotNull
    @Column(name = "standard_price")
    val standardPrice: Int,

    @NotNull
    @Column(name = "max_discount_amount")
    val maxDiscountAmount: Int,

    @NotNull
    @Column(name = "start_date")
    val startDate: LocalDate,

    @NotNull
    @Column(name = "end_date")
    var endDate: LocalDate,

    @NotNull
    @ColumnDefault("false")
    @Column(name = "deleted")
    var deleted: Boolean
) {
    constructor() : this(
        null,
        CouponTypeEntity(),
        "",
        "",
        0.0,
        0,
        0,
        0,
        0,
        LocalDate.now(),
        LocalDate.now(),
        false
    )
}
