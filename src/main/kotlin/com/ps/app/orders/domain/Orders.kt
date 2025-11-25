package com.ps.app.orders.domain

import java.time.LocalDate

data class Orders(
    val id: Long = 0,
    val orderStr: String,
    val price: Int,
    val request: String?,
    val address: String,
    val addressDetail: String,
    val zipcode: Int,
    val desiredDeliveryDate: LocalDate,
    val receiver: String,
    val userId: Long?,
    val sender: String,
    val senderContactNumber: String,
    val receiverContactNumber: String,
    val orderEmail: String?,
    val couponCode: String?,
    val deliveryRate: Int,
    val deductedPoints: Int?,
    val earnedPoints: Int?,
    val deductedCouponPrice: Int?,
    val orderStatus: OrderStatus,
    val details: List<OrderDetail> = emptyList()
) {
    fun changeOrderStatus(newStatus: OrderStatus): Orders {
        return copy(orderStatus = newStatus)
    }

    fun addEarnedPoints(points: Int): Orders {
        return copy(earnedPoints = points)
    }

    fun getTotalAmount(): Int {
        return price + deliveryRate - (deductedPoints ?: 0) - (deductedCouponPrice ?: 0)
    }

    fun isUserOrder(): Boolean = userId != null

    companion object {
        fun create(
            orderStr: String,
            price: Int,
            address: String,
            addressDetail: String,
            zipcode: Int,
            desiredDeliveryDate: LocalDate,
            receiver: String,
            sender: String,
            senderContactNumber: String,
            receiverContactNumber: String,
            orderStatus: OrderStatus,
            userId: Long? = null,
            request: String? = null,
            orderEmail: String? = null,
            couponCode: String? = null,
            deliveryRate: Int = 0,
            deductedPoints: Int? = null,
            deductedCouponPrice: Int? = null
        ): Orders {
            return Orders(
                orderStr = orderStr,
                price = price,
                request = request,
                address = address,
                addressDetail = addressDetail,
                zipcode = zipcode,
                desiredDeliveryDate = desiredDeliveryDate,
                receiver = receiver,
                userId = userId,
                sender = sender,
                senderContactNumber = senderContactNumber,
                receiverContactNumber = receiverContactNumber,
                orderEmail = orderEmail,
                couponCode = couponCode,
                deliveryRate = deliveryRate,
                deductedPoints = deductedPoints,
                earnedPoints = null,
                deductedCouponPrice = deductedCouponPrice,
                orderStatus = orderStatus
            )
        }
    }
}
