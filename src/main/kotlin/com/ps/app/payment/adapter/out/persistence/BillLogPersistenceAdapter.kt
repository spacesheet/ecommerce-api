package com.ps.app.payment.adapter.out.persistence

import com.ps.app.payment.domain.BillLog
import com.ps.app.payment.domain.BillStatus
import com.ps.app.orders.adapter.out.persistence.OrderJpaRepository
import com.ps.app.payment.application.port.out.BillLogPort
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class BillLogPersistenceAdapter(
    private val billLogJpaRepository: BillLogJpaRepository,
    private val orderJpaRepository: OrderJpaRepository
) : BillLogPort {

    private val logger = LoggerFactory.getLogger(BillLogPersistenceAdapter::class.java)

    override fun save(billLog: BillLog): BillLog {
        val orderEntity = orderJpaRepository.findById(billLog.orderId).orElse(null)
            ?: throw IllegalArgumentException("Order not found: ${billLog.orderId}")

        val entity = BillLogMapper.toEntity(billLog, orderEntity)
        val savedEntity = billLogJpaRepository.save(entity)

        return BillLogMapper.toDomain(savedEntity)
            ?: throw IllegalStateException("Failed to map saved BillLog entity to domain")
    }

    override fun findById(id: Long): BillLog? {
        return runCatching {
            billLogJpaRepository.findById(id)
                .orElse(null)
                ?.let { BillLogMapper.toDomain(it) }
        }.getOrElse { e ->
            logger.error("Error finding bill log by id $id", e)
            null
        }
    }

    override fun findByOrderId(orderId: Long): List<BillLog> {
        return runCatching {
            billLogJpaRepository.findByOrderId(orderId)
                .mapNotNull { BillLogMapper.toDomain(it) }
        }.getOrElse { e ->
            logger.error("Error finding bill logs by orderId $orderId", e)
            emptyList()
        }
    }

    override fun findByStatus(status: BillStatus): List<BillLog> {
        return runCatching {
            billLogJpaRepository.findByStatus(BillStatusMapper.toEntity(status))
                .mapNotNull { BillLogMapper.toDomain(it) }
        }.getOrElse { e ->
            logger.error("Error finding bill logs by status $status", e)
            emptyList()
        }
    }

    override fun findByPaymentKey(paymentKey: String): BillLog? {
        return runCatching {
            billLogJpaRepository.findByPaymentKey(paymentKey)
                ?.let { BillLogMapper.toDomain(it) }
        }.getOrElse { e ->
            logger.error("Error finding bill log by paymentKey $paymentKey", e)
            null
        }
    }

    override fun findByPayAtBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<BillLog> {
        return runCatching {
            billLogJpaRepository.findByPayAtBetween(startDate, endDate)
                .mapNotNull { BillLogMapper.toDomain(it) }
        }.getOrElse { e ->
            logger.error("Error finding bill logs between $startDate and $endDate", e)
            emptyList()
        }
    }

    override fun findAll(): List<BillLog> {
        return runCatching {
            billLogJpaRepository.findAll()
                .mapNotNull { BillLogMapper.toDomain(it) }
        }.getOrElse { e ->
            logger.error("Error finding all bill logs", e)
            emptyList()
        }
    }

    override fun delete(id: Long) {
        runCatching {
            billLogJpaRepository.deleteById(id)
        }.onFailure { e ->
            logger.error("Error deleting bill log by id $id", e)
            throw IllegalStateException("Failed to delete bill log with id $id", e)
        }
    }

    override fun existsByPaymentKey(paymentKey: String): Boolean {
        return runCatching {
            billLogJpaRepository.existsByPaymentKey(paymentKey)
        }.getOrElse { e ->
            logger.error("Error checking existence of paymentKey $paymentKey", e)
            false
        }
    }
}
