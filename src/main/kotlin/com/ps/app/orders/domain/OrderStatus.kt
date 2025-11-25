package com.ps.app.orders.domain

import java.time.LocalDateTime

data class OrderStatus(
    val id: Int = 0,
    val name: String,
    val updateAt: LocalDateTime
) {
    companion object {
        fun create(name: String): OrderStatus {
            return OrderStatus(
                name = name,
                updateAt = LocalDateTime.now()
            )
        }

        // 주문 상태 상수
        const val PENDING = "PENDING"
        const val CONFIRMED = "CONFIRMED"
        const val PROCESSING = "PROCESSING"
        const val SHIPPED = "SHIPPED"
        const val DELIVERED = "DELIVERED"
        const val CANCELLED = "CANCELLED"
        const val REFUNDED = "REFUNDED"
    }
}
