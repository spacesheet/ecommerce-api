package com.ps.app.point.adapter.out.persistence

import com.ps.app.point.domain.PointPolicy


object PointPolicyMapper {
    fun toDomain(entity: PointPolicyEntity): PointPolicy {
        return PointPolicy(
            id = entity.id,
            name = entity.name,
            point = entity.point,
            rate = entity.rate,
            deleted = entity.deleted
        )
    }

    fun toEntity(domain: PointPolicy): PointPolicyEntity {
        return PointPolicyEntity(
            id = domain.id,
            name = domain.name,
            point = domain.point,
            rate = domain.rate,
            deleted = domain.deleted
        )
    }
}
