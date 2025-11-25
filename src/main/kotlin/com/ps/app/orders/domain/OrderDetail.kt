package com.ps.app.orders.domain


import java.time.LocalDateTime

data class OrderDetail(
    val id: Long = 0,
    val price: Int,
    val quantity: Int,
    val wrap: Boolean,
    val orderStatus: OrderStatus,
    val wrapping: Wrapping?,
    val productId: Long,
    val orderId: Long,
    val createAt: LocalDateTime,
    val updateAt: LocalDateTime
) {
    fun changeOrderStatus(newStatus: OrderStatus): OrderDetail {
        return copy(
            orderStatus = newStatus,
            updateAt = LocalDateTime.now()
        )
    }

    fun getTotalPrice(): Int {
        val wrappingPrice = if (wrap && wrapping != null) wrapping.price else 0
        return (price * quantity) + wrappingPrice
    }

    companion object {
        fun create(
            price: Int,
            quantity: Int,
            wrap: Boolean,
            orderStatus: OrderStatus,
            wrapping: Wrapping?,
            productId: Long,
            orderId: Long
        ): OrderDetail {
            val now = LocalDateTime.now()
            return OrderDetail(
                price = price,
                quantity = quantity,
                wrap = wrap,
                orderStatus = orderStatus,
                wrapping = wrapping,
                productId = productId,
                orderId = orderId,
                createAt = now,
                updateAt = now
            )
        }
    }
}
