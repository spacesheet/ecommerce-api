package com.ps.app.coupon.adapter.out.persistence

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "coupon_type")
class CouponTypeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @NotNull
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    var name: String
) {
    constructor() : this(null, "")
}
