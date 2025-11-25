package com.ps.app.orders.adapter.out.persistence

import com.ps.app.orders.application.port.out.DeliveryPolicyPort
import com.ps.app.orders.domain.DeliveryPolicy
import org.springframework.stereotype.Component

@Component
class DeliveryPolicyPersistenceAdapter(
    private val deliveryPolicyJpaRepository: DeliveryPolicyJpaRepository
) : DeliveryPolicyPort {

    override fun save(deliveryPolicy: DeliveryPolicy): DeliveryPolicy {
        val entity = DeliveryPolicyMapper.toEntity(deliveryPolicy)
        val savedEntity = deliveryPolicyJpaRepository.save(entity)
        return DeliveryPolicyMapper.toDomain(savedEntity)
    }

    override fun findById(id: Int): DeliveryPolicy? {
        return deliveryPolicyJpaRepository.findById(id)
            .map { DeliveryPolicyMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findActivePolicy(): DeliveryPolicy? {
        return deliveryPolicyJpaRepository.findActivePolicy()
            ?.let { DeliveryPolicyMapper.toDomain(it) }
    }

    override fun findAll(): List<DeliveryPolicy> {
        return deliveryPolicyJpaRepository.findAll()
            .map { DeliveryPolicyMapper.toDomain(it) }
    }

    override fun findAllActive(): List<DeliveryPolicy> {
        return deliveryPolicyJpaRepository.findAllActive()
            .map { DeliveryPolicyMapper.toDomain(it) }
    }
}
