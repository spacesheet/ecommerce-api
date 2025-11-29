package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.domain.CouponPolicy
import com.ps.app.coupons.domain.CouponPolicyId

object CouponPolicyMapper {

    fun toDomain(entity: CouponPolicyEntity): CouponPolicy {
        val couponType = CouponTypeMapper.toDomain(entity.couponType)

        return CouponPolicy(
            id = CouponPolicyId(entity.id),
            couponType = couponType,
            name = entity.name,
            discountType = entity.discountType,
            discountRate = entity.discountRate,
            discountAmount = entity.discountAmount,
            period = entity.period,
            standardPrice = entity.standardPrice,
            maxDiscountAmount = entity.maxDiscountAmount,
            startDate = entity.startDate,
            endDate = entity.endDate,
            deleted = entity.deleted
        )
    }

    fun toEntity(
        domain: CouponPolicy,
        couponTypeEntity: CouponTypeEntity
    ): CouponPolicyEntity {
        return CouponPolicyEntity(
            id = domain.id.value,
            couponType = couponTypeEntity,
            name = domain.name,
            discountType = domain.discountType,
            discountRate = domain.discountRate,
            discountAmount = domain.discountAmount,
            period = domain.period,
            standardPrice = domain.standardPrice,
            maxDiscountAmount = domain.maxDiscountAmount,
            startDate = domain.startDate,
            endDate = domain.endDate,
            deleted = domain.deleted
        )
    }

    fun toDomainList(entities: List<CouponPolicyEntity>): List<CouponPolicy> {
        return entities.map { toDomain(it) }
    }

    fun toDomainSafe(entity: CouponPolicyEntity?): CouponPolicy? {
        return entity?.let {
            runCatching {
                toDomain(it)
            }.onFailure { error ->
                println("Failed to map CouponPolicy ${entity.id}: ${error.message}")
            }.getOrNull()
        }
    }
}
