package com.ps.app.orders.adapter.out.persistence

import com.ps.app.orders.domain.Wrapping

object WrappingMapper {
    fun toDomain(entity: WrappingEntity): Wrapping {
        return Wrapping(
            id = entity.id,
            paper = entity.paper,
            price = entity.price,
            deleted = entity.deleted
        )
    }

    fun toEntity(domain: Wrapping): WrappingEntity {
        return WrappingEntity(
            id = domain.id,
            paper = domain.paper,
            price = domain.price,
            deleted = domain.deleted
        )
    }
}
