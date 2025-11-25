package com.ps.app.orders.adapter.out.persistence

import com.ps.app.orders.application.port.out.OrderStatusPort
import com.ps.app.orders.domain.OrderStatus
import org.springframework.stereotype.Component


@Component
class OrderStatusPersistenceAdapter(
    private val orderStatusJpaRepository: OrderStatusJpaRepository
) : OrderStatusPort {

    override fun save(orderStatus: OrderStatus): OrderStatus {
        val entity = OrderStatusMapper.toEntity(orderStatus)
        val savedEntity = orderStatusJpaRepository.save(entity)
        return OrderStatusMapper.toDomain(savedEntity)
    }

    override fun findById(id: Int): OrderStatus? {
        return orderStatusJpaRepository.findById(id)
            .map { OrderStatusMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByName(name: String): OrderStatus? {
        return orderStatusJpaRepository.findByName(name)
            ?.let { OrderStatusMapper.toDomain(it) }
    }

    override fun findAll(): List<OrderStatus> {
        return orderStatusJpaRepository.findAll()
            .map { OrderStatusMapper.toDomain(it) }
    }
}
