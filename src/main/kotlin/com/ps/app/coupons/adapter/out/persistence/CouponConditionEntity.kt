package com.ps.app.coupons.infrastructure.entity

import com.ps.app.coupons.domain.CouponCondition
import com.ps.app.coupons.domain.CouponConditionId
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "coupon_condition")
class CouponConditionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_policy_id", nullable = false)
    val couponPolicy: CouponPolicyEntity,

    @Column(name = "period", nullable = false)
    val period: Int,

    @Column(name = "standard_price", nullable = false)
    val standardPrice: Int,

    @Column(name = "max_discount_amount", nullable = false)
    val maxDiscountAmount: Int,

    @Column(name = "start_date", nullable = false)
    val startDate: LocalDate,

    @Column(name = "end_date", nullable = false)
    val endDate: LocalDate
) {
    companion object {
        fun from(condition: CouponCondition, policyEntity: CouponPolicyEntity): CouponConditionEntity {
            return CouponConditionEntity(
                id = if (condition.id.isNew()) null else condition.id.value,
                couponPolicy = policyEntity,
                period = condition.period,
                standardPrice = condition.standardPrice,
                maxDiscountAmount = condition.maxDiscountAmount,
                startDate = condition.startDate,
                endDate = condition.endDate
            )
        }
    }

    fun toDomain(): CouponCondition {
        return CouponCondition(
            id = id?.let { CouponConditionId(it) } ?: CouponConditionId.NEW,
            period = period,
            standardPrice = standardPrice,
            maxDiscountAmount = maxDiscountAmount,
            startDate = startDate,
            endDate = endDate
        )
    }
}
