package com.ps.app.orders.application.port.out

import com.ps.app.orders.domain.OrderDetail
import com.ps.app.orders.domain.OrderDetailId
import com.ps.app.orders.domain.OrderId
import com.ps.app.orders.domain.OrderStatus
import com.ps.app.products.domain.ProductId
import com.ps.app.user.domain.UserId

interface OrderDetailPort {
    fun save(orderDetail: OrderDetail): OrderDetail
    fun findById(id: OrderDetailId): OrderDetail?
    fun findByOrderId(orderId: OrderId): List<OrderDetail>
    fun findByUserId(userId: UserId): List<OrderDetail>
    fun findByProductId(productId: ProductId): List<OrderDetail>
    fun findByStatus(status: OrderStatus): List<OrderDetail>
    fun findByUserIdAndStatus(userId: UserId, status: OrderStatus): List<OrderDetail>
    fun findReviewableOrderDetails(userId: UserId): List<OrderDetail>
    fun findCancellableByOrderId(orderId: OrderId): List<OrderDetail>
    fun findReturnableByUserId(userId: UserId): List<OrderDetail>
    fun delete(orderDetail: OrderDetail)
    fun deleteById(id: OrderDetailId)
    fun existsByOrderIdAndProductId(orderId: OrderId, productId: ProductId): Boolean
    fun getTotalSoldQuantity(productId: ProductId): Long
    fun calculateOrderTotal(orderId: OrderId): Int
}
