package com.ps.app.orders.application.port.out

import com.ps.app.orders.domain.DeliveryPolicy

interface DeliveryPolicyPort {
    fun save(deliveryPolicy: DeliveryPolicy): DeliveryPolicy
    fun findById(id: Int): DeliveryPolicy?
    fun findActivePolicy(): DeliveryPolicy?
    fun findAll(): List<DeliveryPolicy>
    fun findAllActive(): List<DeliveryPolicy>
}
