package com.ps.app.orders.adapter.out.persistence


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface OrderJpaRepository : JpaRepository<OrdersEntity, Long> {
    fun findByOrderStr(orderStr: String): OrdersEntity?

    @Query("SELECT o FROM OrdersEntity o WHERE o.user.id = :userId")
    fun findByUserId(@Param("userId") userId: Long): List<OrdersEntity>

    fun existsByOrderStr(orderStr: String): Boolean

    override fun findById(id: Long): Optional<OrdersEntity?>
}
