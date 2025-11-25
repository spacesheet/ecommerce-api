package com.ps.app.orders.adapter.out.persistence

import com.ps.app.orders.domain.OrderDetail
import com.ps.app.products.adapter.out.persistence.ProductEntity


object OrderDetailMapper {
    fun toDomain(entity: OrderDetailEntity): OrderDetail? {
        val product = entity.product ?: return null
        val order = entity.order ?: return null
        val orderStatus = entity.orderStatus ?: return null

        return OrderDetail(
            id = entity.id,
            price = entity.price,
            quantity = entity.quantity,
            wrap = entity.wrap,
            orderStatus = OrderStatusMapper.toDomain(orderStatus),
            wrapping = entity.wrapping?.let { WrappingMapper.toDomain(it) },
            productId = product.id,
            orderId = order.id,
            createAt = entity.createAt,
            updateAt = entity.updateAt
        )
    }

    fun toEntity(
        domain: OrderDetail,
        orderStatusEntity: OrderStatusEntity,
        wrappingEntity: WrappingEntity?,
        productEntity: ProductEntity,
        orderEntity: OrdersEntity
    ): OrderDetailEntity {
        return OrderDetailEntity(
            id = domain.id,
            price = domain.price,
            quantity = domain.quantity,
            wrap = domain.wrap,
            orderStatus = orderStatusEntity,
            wrapping = wrappingEntity,
            product = productEntity,
            order = orderEntity,
            createAt = domain.createAt,
            updateAt = domain.updateAt
        )
    }
}
