package com.ps.app.point.adapter.out.persistence

import com.ps.app.point.application.port.out.PointLogPort
import com.ps.app.point.domain.PointLog
import org.springframework.stereotype.Component


@Component
class PointLogRepositoryAdapter(
    private val jpaRepository: PointLogJpaRepository
) : PointLogPort {

    override fun save(pointLog: PointLog): PointLog {
        val entity = PointLogMapper.toEntity(pointLog)
        val savedEntity = jpaRepository.save(entity)
        return PointLogMapper.toDomain(savedEntity)
    }

    override fun findById(id: Long): PointLog? {
        return jpaRepository.findById(id)
            .map { PointLogMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAllByUserId(userId: Long): List<PointLog> {
        return jpaRepository.findAllByUserId(userId)
            .map { PointLogMapper.toDomain(it) }
    }

    override fun findAllByUserIdOrderByCreatedAtDesc(userId: Long): List<PointLog> {
        return jpaRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
            .map { PointLogMapper.toDomain(it) }
    }
}
