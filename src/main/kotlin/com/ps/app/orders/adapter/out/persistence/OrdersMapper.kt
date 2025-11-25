package com.ps.app.orders.adapter.out.persistence

import com.ps.app.orders.domain.Orders
import com.ps.app.user.adapter.out.persistence.UserEntity

object OrdersMapper {
    fun toDomain(entity: OrdersEntity): Orders? {
        if (entity.orderStatus == null) {
            println("OrderStatus is null for order ${entity.id}")
            return null
        }

        return Orders(
            id = entity.id,
            orderStr = entity.orderStr,
            price = entity.price,
            request = entity.request,
            address = entity.address,
            addressDetail = entity.addressDetail,
            zipcode = entity.zipcode,
            desiredDeliveryDate = entity.desiredDeliveryDate,
            receiver = entity.receiver,
            userId = entity.user?.id,
            sender = entity.sender,
            senderContactNumber = entity.senderContactNumber,
            receiverContactNumber = entity.receiverContactNumber,
            orderEmail = entity.orderEmail,
            couponCode = entity.couponCode,
            deliveryRate = entity.deliveryRate,
            deductedPoints = entity.deductedPoints,
            earnedPoints = entity.earnedPoints,
            deductedCouponPrice = entity.deductedCouponPrice,
            orderStatus = OrderStatusMapper.toDomain(entity.orderStatus),
            details = entity.details?.mapNotNull {
                runCatching { OrderDetailMapper.toDomain(it) }.getOrNull()
            } ?: emptyList()
        )
    }

    fun toEntity(domain: Orders, userEntity: UserEntity?, orderStatusEntity: OrderStatusEntity): OrdersEntity {
        return OrdersEntity(
            id = domain.id,
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
            orderStatus = orderStatusEntity
        )
    }
}
