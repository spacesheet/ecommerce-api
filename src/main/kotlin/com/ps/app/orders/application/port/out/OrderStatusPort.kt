package com.ps.app.orders.application.port.out

import com.ps.app.orders.domain.OrderStatus


interface OrderStatusPort {
    fun save(orderStatus: OrderStatus): OrderStatus
    fun findById(id: Int): OrderStatus?
    fun findByName(name: String): OrderStatus?
    fun findAll(): List<OrderStatus>
}
