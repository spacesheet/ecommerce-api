package com.ps.app.orders.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface OrderStatusJpaRepository : JpaRepository<OrderStatusEntity, Int> {
    fun findByName(name: String): OrderStatusEntity?
    fun existsByName(name: String): Boolean
}
