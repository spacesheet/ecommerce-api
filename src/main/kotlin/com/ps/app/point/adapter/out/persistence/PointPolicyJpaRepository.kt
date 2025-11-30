package com.ps.app.point.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface PointPolicyJpaRepository : JpaRepository<PointPolicyEntity, Long> {
    fun findByName(name: String): PointPolicyEntity?

    @Query("SELECT p FROM PointPolicyEntity p WHERE p.deleted = false")
    fun findAllActive(): List<PointPolicyEntity>
}
