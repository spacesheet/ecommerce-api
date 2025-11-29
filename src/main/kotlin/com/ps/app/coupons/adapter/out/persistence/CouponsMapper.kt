package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.domain.Coupons
import com.ps.app.coupons.domain.CouponsId
import com.ps.app.user.domain.UserId

object CouponsMapper {

    fun toDomain(entity: CouponsEntity): Coupons {
        val couponPolicy = CouponPolicyMapper.toDomain(entity.couponPolicy)

        return Coupons(
            id = CouponsId(entity.id),
            ownerId = UserId(entity.ownerId),
            couponPolicy = couponPolicy,
            couponCode = entity.couponCode,
            createDate = entity.createDate,
            expireDate = entity.expireDate,
            couponStatus = entity.couponStatus
        )
    }

    fun toEntity(
        domain: Coupons,
        couponPolicyEntity: CouponPolicyEntity
    ): CouponsEntity {
        return CouponsEntity(
            id = domain.id.value,
            ownerId = domain.ownerId.value,
            couponPolicy = couponPolicyEntity,
            couponCode = domain.couponCode,
            createDate = domain.createDate,
            expireDate = domain.expireDate,
            couponStatus = domain.couponStatus
        )
    }

    fun toDomainList(entities: List<CouponsEntity>): List<Coupons> {
        return entities.map { toDomain(it) }
    }

    fun toDomainSafe(entity: CouponsEntity?): Coupons? {
        return entity?.let {
            runCatching {
                toDomain(it)
            }.onFailure { error ->
                println("Failed to map Coupons ${entity.id}: ${error.message}")
            }.getOrNull()
        }
    }
}
