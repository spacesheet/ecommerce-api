package com.ps.app.orders.adapter.out.persistence

import com.ps.app.orders.domain.DeliveryPolicy


object DeliveryPolicyMapper {
    fun toDomain(entity: DeliveryPolicyEntity): DeliveryPolicy {
        return DeliveryPolicy(
            id = entity.id,
            name = entity.name,
            standardPrice = entity.standardPrice,
            policyPrice = entity.policyPrice,
            deleted = entity.deleted
        )
    }

    fun toEntity(domain: DeliveryPolicy): DeliveryPolicyEntity {
        return DeliveryPolicyEntity(
            id = domain.id,
            name = domain.name,
            standardPrice = domain.standardPrice,
            policyPrice = domain.policyPrice,
            deleted = domain.deleted
        )
    }
}
