package com.ps.app.coupons.infrastructure.entity

import com.ps.app.coupons.domain.AmountCouponPolicy
import com.ps.app.coupons.domain.CouponPolicyId
import com.ps.app.coupons.domain.constant.DiscountType
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
@DiscriminatorValue("FIXED_AMOUNT")
class AmountCouponPolicyEntity(
    id: Int? = null,
    couponType: com.ps.app.coupons.domain.CouponType,
    name: String,
    conditions: MutableList<CouponConditionEntity> = mutableListOf(),
    deleted: Boolean = false,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "discount_amount", nullable = false)
    val discountAmount: Int
) : CouponPolicyEntity(
    id = id,
    couponType = couponType,
    name = name,
    conditions = conditions,
    deleted = deleted,
    createdAt = createdAt,
    updatedAt = updatedAt
) {
    override val discountType: DiscountType = DiscountType.FIXED_AMOUNT

    companion object {
        fun from(domain: AmountCouponPolicy): AmountCouponPolicyEntity {
            val entity = AmountCouponPolicyEntity(
                id = if (domain.id.isNew()) null else domain.id.value,
                couponType = domain.couponType,
                name = domain.name,
                deleted = domain.deleted,
                discountAmount = domain.discountAmount
            )

            domain.conditions.forEach { condition ->
                val conditionEntity = CouponConditionEntity.from(condition, entity)
                entity.addCondition(conditionEntity)
            }

            return entity
        }
    }

    fun toDomain(): AmountCouponPolicy {
        return AmountCouponPolicy(
            policyId = id?.let { CouponPolicyId(it) } ?: CouponPolicyId.NEW,
            policyType = couponType,
            policyName = name,
            discountAmount = discountAmount,
            policyConditions = conditions.map { it.toDomain() },
            isDeleted = deleted
        )
    }
}
