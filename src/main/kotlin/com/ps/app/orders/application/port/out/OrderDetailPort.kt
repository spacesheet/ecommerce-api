package com.ps.app.orders.application.port.out

import com.ps.app.orders.domain.OrderDetail
import com.ps.app.orders.domain.OrderDetailId

interface OrderDetailPort {
    fun save(orderDetail: OrderDetail): OrderDetail
    fun findById(id: OrderDetailId): OrderDetail?
    fun findByOrderId(orderId: Long): List<OrderDetail>
    fun saveAll(orderDetails: List<OrderDetail>): List<OrderDetail>
    fun delete(id: Long)
}
