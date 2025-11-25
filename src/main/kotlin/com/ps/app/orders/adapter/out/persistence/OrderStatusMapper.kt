package com.ps.app.orders.adapter.out.persistence

import com.ps.app.orders.domain.OrderStatus


object OrderStatusMapper {
    fun toDomain(entity: OrderStatusEntity): OrderStatus {
        return OrderStatus(
            id = entity.id,
            name = entity.name,
            updateAt = entity.updateAt
        )
    }

    fun toEntity(domain: OrderStatus): OrderStatusEntity {
        return OrderStatusEntity(
            id = domain.id,
            name = domain.name,
            updateAt = domain.updateAt
        )
    }
}
