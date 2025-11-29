package com.ps.app.orders.domain

import com.ps.app.products.domain.Product
import com.ps.app.user.domain.UserId
import java.time.LocalDateTime

data class OrderDetail(
    val id: OrderDetailId = OrderDetailId.NEW,
    val price: Int,
    val quantity: Int,
    val wrap: Boolean,
    val status: OrderStatus,  // Enum 직접 사용
    val wrapping: Wrapping?,
    val product: Product,
    val order: Orders,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun create(
            price: Int,
            quantity: Int,
            wrap: Boolean,
            wrapping: Wrapping?,
            product: Product,
            order: Orders
        ): OrderDetail {
            require(price > 0) { "가격은 0보다 커야 합니다" }
            require(quantity > 0) { "수량은 0보다 커야 합니다" }

            val now = LocalDateTime.now()
            return OrderDetail(
                id = OrderDetailId.NEW,
                price = price,
                quantity = quantity,
                wrap = wrap,
                status = OrderStatus.PENDING,
                wrapping = wrapping,
                product = product,
                order = order,
                createdAt = now,
                updatedAt = now
            )
        }
    }

    fun changeStatus(newStatus: OrderStatus): OrderDetail {
        require(status.canTransitionTo(newStatus)) {
            "${status.displayName}에서 ${newStatus.displayName}(으)로 변경할 수 없습니다"
        }
        return copy(
            status = newStatus,
            updatedAt = LocalDateTime.now()
        )
    }

    fun confirm(): OrderDetail = changeStatus(OrderStatus.CONFIRMED)
    fun startProcessing(): OrderDetail = changeStatus(OrderStatus.PROCESSING)
    fun startShipping(): OrderDetail = changeStatus(OrderStatus.SHIPPING)
    fun completeShipping(): OrderDetail = changeStatus(OrderStatus.SHIPPED)
    fun completeDelivery(): OrderDetail = changeStatus(OrderStatus.DELIVERED)
    fun cancel(): OrderDetail = changeStatus(OrderStatus.CANCELLED)
    fun refund(): OrderDetail = changeStatus(OrderStatus.REFUNDED)
    fun returnOrder(): OrderDetail = changeStatus(OrderStatus.RETURNED)

    fun canModify(): Boolean = status.isModifiable()
    fun canCancel(): Boolean = status.isCancellable()
    fun canReturn(): Boolean = status.isReturnable()
    fun canReview(): Boolean = status == OrderStatus.DELIVERED

    fun belongsToUser(userId: UserId): Boolean = order.userId == userId
    fun belongsToUser(userId: Long): Boolean = order.userId.value == userId

    fun getTotalPrice(): Int {
        val wrappingPrice = if (wrap && wrapping != null) wrapping.price else 0
        return (price * quantity) + wrappingPrice
    }
}
