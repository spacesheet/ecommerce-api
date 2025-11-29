package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.domain.CouponType
import com.ps.app.coupons.domain.CouponTypeId
import com.ps.app.coupons.domain.constant.CouponScope

/**
 * Domain ↔ Entity 변환
 */
object CouponTypeMapper {

    fun toDomain(entity: CouponTypeEntity): CouponType {
        return CouponType(
            id = CouponTypeId(entity.id),
            name = CouponScope.valueOf(entity.name)
        )
    }

    fun toEntity(domain: CouponType): CouponTypeEntity {
        return CouponTypeEntity(
            id = domain.id.value,
            name = domain.name.name
        )
    }
}
