package com.ps.app.point.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface PointLogJpaRepository : JpaRepository<PointLogEntity, Long> {
    fun findAllByUserId(userId: Long): List<PointLogEntity>
    fun findAllByUserIdOrderByCreatedAtDesc(userId: Long): List<PointLogEntity>
}
