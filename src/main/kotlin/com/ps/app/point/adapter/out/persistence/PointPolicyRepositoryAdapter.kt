package com.ps.app.point.adapter.out.persistence

import com.ps.app.point.application.port.out.PointPolicyPort
import com.ps.app.point.domain.PointPolicy
import org.springframework.stereotype.Component


@Component
class PointPolicyRepositoryAdapter(
    private val jpaRepository: PointPolicyJpaRepository
) : PointPolicyPort {

    override fun save(pointPolicy: PointPolicy): PointPolicy {
        val entity = PointPolicyMapper.toEntity(pointPolicy)
        val savedEntity = jpaRepository.save(entity)
        return PointPolicyMapper.toDomain(savedEntity)
    }

    override fun findById(id: Long): PointPolicy? {
        return jpaRepository.findById(id)
            .map { PointPolicyMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByName(name: String): PointPolicy? {
        return jpaRepository.findByName(name)
            ?.let { PointPolicyMapper.toDomain(it) }
    }

    override fun findAll(): List<PointPolicy> {
        return jpaRepository.findAll()
            .map { PointPolicyMapper.toDomain(it) }
    }

    override fun findAllActive(): List<PointPolicy> {
        return jpaRepository.findAllActive()
            .map { PointPolicyMapper.toDomain(it) }
    }
}
