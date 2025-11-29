package com.ps.app.orders.adapter.out.persistence

import com.ps.app.orders.application.port.out.OrdersPort
import com.ps.app.orders.domain.Orders
import com.ps.app.user.adapter.out.persistence.UserJpaRepository
import org.springframework.stereotype.Component
import org.slf4j.LoggerFactory

@Component
class OrdersPersistenceAdapter(
    private val orderJpaRepository: OrderJpaRepository,
    private val userJpaRepository: UserJpaRepository,
) : OrdersPort {

    private val logger = LoggerFactory.getLogger(OrdersPersistenceAdapter::class.java)

    override fun save(order: Orders): Orders {
        val userEntity = order.userId?.let { userJpaRepository.findById(it).orElse(null) }

        val entity = OrdersMapper.toEntity(order, userEntity, order.orderStatus)
        val savedEntity = orderJpaRepository.save(entity)

        return OrdersMapper.toDomain(savedEntity)
            ?: throw IllegalStateException("Failed to map saved order entity to domain")
    }

    override fun findById(id: Long): Orders? {
        return runCatching {
            orderJpaRepository.findById(id)
                .orElse(null)
                ?.let { OrdersMapper.toDomain(it) }
        }.getOrElse { e ->
            logger.error("Error finding order by id $id", e)
            null
        }
    }

    override fun findByOrderStr(orderStr: String): Orders? {
        return runCatching {
            orderJpaRepository.findByOrderStr(orderStr)
                ?.let { OrdersMapper.toDomain(it) }
        }.getOrElse { e ->
            logger.error("Error finding order by orderStr $orderStr", e)
            null
        }
    }

    override fun findByUserId(userId: Long): List<Orders> {
        return runCatching {
            orderJpaRepository.findByUserId(userId)
                .mapNotNull { OrdersMapper.toDomain(it) }
        }.getOrElse { e ->
            logger.error("Error finding orders by userId $userId", e)
            emptyList()
        }
    }

    override fun findAll(): List<Orders> {
        return runCatching {
            orderJpaRepository.findAll()
                .mapNotNull { OrdersMapper.toDomain(it) }
        }.getOrElse { e ->
            logger.error("Error finding all orders", e)
            emptyList()
        }
    }

    override fun delete(id: Long) {
        runCatching {
            orderJpaRepository.deleteById(id)
        }.onFailure { e ->
            logger.error("Error deleting order by id $id", e)
            throw IllegalStateException("Failed to delete order with id $id", e)
        }
    }

    override fun existsByOrderStr(orderStr: String): Boolean {
        return runCatching {
            orderJpaRepository.existsByOrderStr(orderStr)
        }.getOrElse { e ->
            logger.error("Error checking existence of orderStr $orderStr", e)
            false
        }
    }
}
