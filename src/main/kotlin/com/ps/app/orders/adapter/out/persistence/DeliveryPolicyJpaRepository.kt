package com.ps.app.orders.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DeliveryPolicyJpaRepository : JpaRepository<DeliveryPolicyEntity, Int> {

    @Query("SELECT dp FROM DeliveryPolicyEntity dp WHERE dp.deleted = false")
    fun findAllActive(): List<DeliveryPolicyEntity>

    @Query("SELECT dp FROM DeliveryPolicyEntity dp WHERE dp.deleted = false ORDER BY dp.id DESC LIMIT 1")
    fun findActivePolicy(): DeliveryPolicyEntity?
}
