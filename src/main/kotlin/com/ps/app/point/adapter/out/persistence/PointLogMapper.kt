package com.ps.app.point.adapter.out.persistence

import com.ps.app.point.domain.PointLog


object PointLogMapper {
    fun toDomain(entity: PointLogEntity): PointLog {
        return PointLog(
            id = entity.id,
            userId = entity.userId,
            createAt = entity.createAt,
            inquiry = entity.inquiry,
            delta = entity.delta,
            balance = entity.balance
        )
    }

    fun toEntity(domain: PointLog): PointLogEntity {
        return PointLogEntity(
            id = domain.id,
            userId = domain.userId,
            createAt = domain.createAt,
            inquiry = domain.inquiry,
            delta = domain.delta,
            balance = domain.balance
        )
    }
}
