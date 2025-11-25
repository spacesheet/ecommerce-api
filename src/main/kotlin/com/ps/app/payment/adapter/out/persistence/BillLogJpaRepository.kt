package com.ps.app.payment.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface BillLogJpaRepository : JpaRepository<BillLogEntity, Long> {

    @Query("SELECT b FROM BillLogEntity b WHERE b.order.id = :orderId")
    fun findByOrderId(@Param("orderId") orderId: Long): List<BillLogEntity>

    fun findByStatus(status: BillStatusEntity): List<BillLogEntity>

    fun findByPaymentKey(paymentKey: String): BillLogEntity?

    @Query("SELECT b FROM BillLogEntity b WHERE b.payAt BETWEEN :startDate AND :endDate")
    fun findByPayAtBetween(
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime
    ): List<BillLogEntity>

    fun existsByPaymentKey(paymentKey: String): Boolean

    @Query("SELECT b FROM BillLogEntity b JOIN FETCH b.order WHERE b.id = :id")
    fun findByIdWithOrder(@Param("id") id: Long): BillLogEntity?

    @Query("SELECT b FROM BillLogEntity b WHERE b.order.id = :orderId AND b.status = :status")
    fun findByOrderIdAndStatus(
        @Param("orderId") orderId: Long,
        @Param("status") status: BillStatusEntity
    ): List<BillLogEntity>
}
