package com.ps.app.orders.application.port.out

import com.ps.app.orders.domain.OrderDetail

interface OrderDetailPort {
    fun save(orderDetail: OrderDetail): OrderDetail
    fun findById(id: Long): OrderDetail?
    fun findByOrderId(orderId: Long): List<OrderDetail>
    fun saveAll(orderDetails: List<OrderDetail>): List<OrderDetail>
    fun delete(id: Long)
}
