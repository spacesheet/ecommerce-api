package com.ps.app.payment.adapter.out.persistence

import com.ps.app.orders.adapter.out.persistence.OrdersEntity
import com.ps.app.payment.adapter.out.persistence.BillLogEntity
import com.ps.app.payment.domain.BillLog

object BillLogMapper {
    fun toDomain(entity: BillLogEntity): BillLog? {
        return try {
            BillLog(
                id = entity.id,
                payment = entity.payment,
                price = entity.price,
                payAt = entity.payAt,
                orderId = entity.order.id,
                status = BillStatusMapper.toDomain(entity.status),
                paymentKey = entity.paymentKey,
                cancelReason = entity.cancelReason
            )
        } catch (e: Exception) {
            println("Error mapping BillLogEntity to BillLog: ${e.message}")
            null
        }
    }

    fun toEntity(domain: BillLog, orderEntity: OrdersEntity): BillLogEntity {
        return BillLogEntity(
            id = domain.id,
            payment = domain.payment,
            price = domain.price,
            payAt = domain.payAt,
            order = orderEntity,
            status = BillStatusMapper.toEntity(domain.status),
            paymentKey = domain.paymentKey,
            cancelReason = domain.cancelReason
        )
    }
}
