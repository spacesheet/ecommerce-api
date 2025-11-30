package com.ps.app.point.application.port.out

import com.ps.app.point.domain.PointPolicy

interface PointPolicyPort {
    fun save(pointPolicy: PointPolicy): PointPolicy
    fun findById(id: Long): PointPolicy?
    fun findByName(name: String): PointPolicy?
    fun findAll(): List<PointPolicy>
    fun findAllActive(): List<PointPolicy>
}
