package com.ps.app.coupon.adapter.out.persistence

import com.ps.app.coupon.domain.CouponType
import com.ps.app.coupon.domain.constant.CouponScope

/**
 * Domain ↔ Entity 변환
 */
object CouponTypeMapper {

    fun toDomain(entity: CouponTypeEntity): CouponType {
        return CouponType(
            id = entity.id,
            name = CouponScope.valueOf(entity.name)
        )
    }

    fun toEntity(domain: CouponType): CouponTypeEntity {
        return CouponTypeEntity(
            id = domain.id,
            name = domain.name.name
        )
    }
}
