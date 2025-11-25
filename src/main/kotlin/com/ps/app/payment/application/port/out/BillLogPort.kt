package com.ps.app.payment.application.port.out

import com.ps.app.payment.domain.BillLog
import com.ps.app.payment.domain.BillStatus
import java.time.LocalDateTime

interface BillLogPort {
    fun save(billLog: BillLog): BillLog
    fun findById(id: Long): BillLog?
    fun findByOrderId(orderId: Long): List<BillLog>
    fun findByStatus(status: BillStatus): List<BillLog>
    fun findByPaymentKey(paymentKey: String): BillLog?
    fun findByPayAtBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<BillLog>
    fun findAll(): List<BillLog>
    fun delete(id: Long)
    fun existsByPaymentKey(paymentKey: String): Boolean
}
