package com.ps.app.orders.adapter.out.persistence

import com.ps.app.orders.domain.Order
import com.ps.app.orders.domain.OrderId
import com.ps.app.orders.domain.OrderStatus
import com.ps.app.orders.domain.Orders
import com.ps.app.user.adapter.out.persistence.UserEntity
import com.ps.app.user.adapter.out.persistence.UserMapper
import java.time.LocalDateTime
import kotlin.collections.emptyList
import kotlin.collections.mapNotNull

object OrdersMapper {

    /**
     * Entity -> Domain 변환
     */
    fun toDomain(entity: OrdersEntity): Orders {
        // OrderStatus 검증
        requireNotNull(entity.orderStatus) {
            "OrderStatus is null for order ${entity.id}"
        }

        // User 정보 변환
        val user = entity.user?.let { userEntity ->
            UserMapper.toDomain(userEntity)
        }

        // OrderDetail 변환 - 실패한 항목은 로깅하고 건너뜀
        val orderDetails = entity.details?.mapNotNull { detailEntity ->
            runCatching {
                OrderDetailMapper.toDomain(detailEntity)
            }.onFailure { error ->
                println("Failed to map OrderDetail ${detailEntity.id}: ${error.message}")
            }.getOrNull()
        } ?: emptyList()

        return Orders(
            id = OrderId(entity.id),
            orderStr = entity.orderStr,
            price = entity.price,
            request = entity.request,
            address = entity.address,
            addressDetail = entity.addressDetail,
            zipcode = entity.zipcode,
            desiredDeliveryDate = entity.desiredDeliveryDate,
            receiver = entity.receiver,
            userId = user,
            sender = entity.sender,
            senderContactNumber = entity.senderContactNumber,
            receiverContactNumber = entity.receiverContactNumber,
            orderEmail = entity.orderEmail,
            couponCode = entity.couponCode,
            deliveryRate = entity.deliveryRate,
            deductedPoints = entity.deductedPoints,
            earnedPoints = entity.earnedPoints,
            deductedCouponPrice = entity.deductedCouponPrice,
            orderStatus = OrderStatus.valueOf(entity.orderStatus.name),
            orderDetails = orderDetails,
            createAt = LocalDateTime.now(),
            updateAt = LocalDateTime.now()
        )
    }

    /**
     * Domain -> Entity 변환
     */
    fun toEntity(
        domain: Orders,
        userEntity: UserEntity?
    ): OrdersEntity {
        return OrdersEntity(
            id = domain.id.value,
            orderStr = domain.orderStr,
            price = domain.price,
            request = domain.request,
            address = domain.address,
            addressDetail = domain.addressDetail,
            zipcode = domain.zipcode,
            desiredDeliveryDate = domain.desiredDeliveryDate,
            receiver = domain.receiver,
            user = userEntity,
            sender = domain.sender,
            senderContactNumber = domain.senderContactNumber,
            receiverContactNumber = domain.receiverContactNumber,
            orderEmail = domain.orderEmail,
            couponCode = domain.couponCode,
            deliveryRate = domain.deliveryRate,
            deductedPoints = domain.deductedPoints,
            earnedPoints = domain.earnedPoints,
            deductedCouponPrice = domain.deductedCouponPrice,
            orderStatus = domain.orderStatus,
            createAt = domain.createAt,
            updateAt = domain.updateAt
        )
    }

    /**
     * 여러 Entity를 Domain으로 변환 (실패한 항목 제외)
     */
    fun toDomainList(entities: List<OrdersEntity>): List<Order> {
        return entities.mapNotNull { entity ->
            runCatching {
                toDomain(entity)
            }.onFailure { error ->
                println("Failed to map Order ${entity.id}: ${error.message}")
            }.getOrNull()
        }
    }

    /**
     * 여러 Entity를 Domain으로 변환 (실패 시 예외 발생)
     */
    fun toDomainListStrict(entities: List<OrdersEntity>): List<Order> {
        return entities.map { toDomain(it) }
    }
}
