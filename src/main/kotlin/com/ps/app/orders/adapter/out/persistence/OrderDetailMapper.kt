package com.ps.app.orders.adapter.out.persistence

import com.ps.app.infrastructure.persistence.entity.OrderDetailEntity
import com.ps.app.orders.domain.OrderDetail
import com.ps.app.orders.domain.OrderDetailId
import com.ps.app.products.adapter.out.persistence.ProductEntity
import com.ps.app.products.adapter.out.persistence.ProductMapper

object OrderDetailMapper {

    fun toDomain(entity: OrderDetailEntity): OrderDetail {
        // Product 변환
        val product = ProductMapper.toDomain(entity.product)

        // Order 변환 (순환 참조 방지를 위해 간단한 정보만)
        val order = OrdersMapper.toDomainWithoutDetails(entity.order)

        // Wrapping 변환
        val wrapping = entity.wrapping?.let { wrappingEntity ->
            WrappingMapper.toDomain(wrappingEntity)
        }

        return OrderDetail(
            id = OrderDetailId(entity.id.value),
            price = entity.price,
            quantity = entity.quantity,
            wrap = entity.wrap,
            orderStatus = entity.orderStatus,
            wrapping = wrapping,
            product = product,
            order = order,
            createAt = entity.createAt,
            updateAt = entity.updateAt
        )
    }

    fun toEntity(
        domain: OrderDetail,
        orderEntity: OrdersEntity,
        productEntity: ProductEntity,
        wrappingEntity: WrappingEntity?
    ): OrderDetailEntity {
        return OrderDetailEntity(
            id = domain.id,
            price = domain.price,
            quantity = domain.quantity,
            wrap = domain.wrap,
            orderStatus = domain.orderStatus,
            wrapping = wrappingEntity,
            product = productEntity,
            order = orderEntity,
            createAt = domain.createAt,
            updateAt = domain.updateAt
        )
    }

    fun toDomainList(entities: List<OrderDetailEntity>): List<OrderDetail> {
        return entities.mapNotNull { entity ->
            runCatching {
                toDomain(entity)
            }.onFailure { error ->
                println("Failed to map OrderDetail ${entity.id}: ${error.message}")
            }.getOrNull()
        }
    }
}
