package com.ps.app.coupons.infrastructure.entity

import com.ps.app.coupons.adapter.out.persistence.CouponTypeConverter
import com.ps.app.coupons.domain.CouponType
import com.ps.app.coupons.domain.constant.DiscountType
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "coupon_policy")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discount_type", discriminatorType = DiscriminatorType.STRING)
@EntityListeners(AuditingEntityListener::class)
abstract class CouponPolicyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open val id: Int? = null,

    @Column(name = "coupon_type", nullable = false, length = 50)
    @Convert(converter = CouponTypeConverter::class)
    @Enumerated(EnumType.STRING)
    open val couponType: CouponType,

    @Column(name = "name", nullable = false, length = 30)
    open val name: String,

    @OneToMany(
        mappedBy = "couponPolicy",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    open val conditions: MutableList<CouponConditionEntity> = mutableListOf(),

    @Column(name = "deleted", nullable = false)
    open val deleted: Boolean = false,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    open val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    open val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    @get:Column(name = "discount_type", insertable = false, updatable = false)
    @get:Enumerated(EnumType.STRING)
    abstract val discountType: DiscountType

    fun addCondition(condition: CouponConditionEntity) {
        conditions.add(condition)
    }

    fun clearConditions() {
        conditions.clear()
    }
}
