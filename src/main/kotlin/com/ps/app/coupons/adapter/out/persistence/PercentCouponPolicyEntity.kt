package com.ps.app.coupons.infrastructure.entity

import com.ps.app.coupons.domain.CouponPolicyId
import com.ps.app.coupons.domain.PercentCouponPolicy
import com.ps.app.coupons.domain.constant.DiscountType
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
@DiscriminatorValue("PERCENTAGE")
class PercentCouponPolicyEntity(
    id: Int? = null,
    couponType: com.ps.app.coupons.domain.CouponType,
    name: String,
    conditions: MutableList<CouponConditionEntity> = mutableListOf(),
    deleted: Boolean = false,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "discount_rate", nullable = false)
    val discountRate: Double
) : CouponPolicyEntity(
    id = id,
    couponType = couponType,
    name = name,
    conditions = conditions,
    deleted = deleted,
    createdAt = createdAt,
    updatedAt = updatedAt
) {
    override val discountType: DiscountType = DiscountType.PERCENTAGE

    companion object {
        fun from(domain: PercentCouponPolicy): PercentCouponPolicyEntity {
            val entity = PercentCouponPolicyEntity(
                id = if (domain.id.isNew()) null else domain.id.value,
                couponType = domain.couponType,
                name = domain.name,
                deleted = domain.deleted,
                discountRate = domain.discountRate
            )

            domain.conditions.forEach { condition ->
                val conditionEntity = CouponConditionEntity.from(condition, entity)
                entity.addCondition(conditionEntity)
            }

            return entity
        }
    }

    fun toDomain(): PercentCouponPolicy {
        return PercentCouponPolicy(
            policyId = id?.let { CouponPolicyId(it) } ?: CouponPolicyId.NEW,
            policyType = couponType,
            policyName = name,
            discountRate = discountRate,
            policyConditions = conditions.map { it.toDomain() },
            isDeleted = deleted
        )
    }
}
