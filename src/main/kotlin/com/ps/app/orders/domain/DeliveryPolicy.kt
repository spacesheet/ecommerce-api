package com.ps.app.orders.domain

data class DeliveryPolicy(
    val id: Int = 0,
    val name: String?,
    val standardPrice: Int,
    val policyPrice: Int,
    val deleted: Boolean = false
) {
    fun delete(): DeliveryPolicy {
        return copy(deleted = true)
    }

    fun isActive(): Boolean = !deleted

    fun calculateDeliveryFee(orderAmount: Int): Int {
        return if (orderAmount >= standardPrice) 0 else policyPrice
    }

    companion object {
        fun create(
            name: String?,
            standardPrice: Int,
            policyPrice: Int
        ): DeliveryPolicy {
            return DeliveryPolicy(
                name = name,
                standardPrice = standardPrice,
                policyPrice = policyPrice,
                deleted = false
            )
        }
    }
}
