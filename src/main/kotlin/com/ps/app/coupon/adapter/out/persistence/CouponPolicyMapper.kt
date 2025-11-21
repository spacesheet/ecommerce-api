package com.ps.app.coupon.adapter.out.persistence

import com.ps.app.coupon.domain.CouponPolicy
import com.ps.app.coupon.domain.constant.DiscountType
import com.ps.app.coupon.infrastructure.persistence.entity.CouponPolicyEntity

object CouponPolicyMapper {

    fun toDomain(entity: CouponPolicyEntity): CouponPolicy {
        return CouponPolicy(
            id = entity.id,
            couponTypeId = entity.couponType.id
                ?: throw IllegalStateException("CouponType id cannot be null"),
            name = entity.name,
            discountType = DiscountType.valueOf(entity.discountType),
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
        couponType: CouponTypeEntity
    ): CouponPolicyEntity {
        return CouponPolicyEntity(
            id = domain.id,
            couponType = couponType,
            name = domain.name,
            discountType = domain.discountType.name,
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
}
