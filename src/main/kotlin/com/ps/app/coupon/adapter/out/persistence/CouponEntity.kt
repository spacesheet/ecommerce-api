// Infrastructure Layer (infrastructure/persistence/entity/CouponEntity.kt)
package com.ps.app.coupon.infrastructure.persistence

import com.ps.app.user.adapter.out.persistence.UserEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

@Entity
@Table(name = "coupon")
class CouponEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    val owner: UserEntity,

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_policy_id")
    val couponPolicy: CouponPolicyEntity,

    @NotNull
    @Column(unique = true, length = 20)
    val couponCode: String,

    @NotNull
    @Column(name = "create_date")
    val createDate: LocalDate,

    @NotNull
    @Column(name = "expire_date")
    val expireDate: LocalDate,

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var status: String
) {
    protected constructor() : this(
        null,
        UserEntity(),
        CouponPolicyEntity(),
        "",
        LocalDate.now(),
        LocalDate.now(),
        ""
    )
}
