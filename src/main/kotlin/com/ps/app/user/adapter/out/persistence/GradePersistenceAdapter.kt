package com.ps.app.user.adapter.out.persistence

import com.ps.app.user.application.port.out.GradePort
import com.ps.app.user.domain.Grade
import org.springframework.stereotype.Component

@Component
class GradePersistenceAdapter(
    private val gradeRepository: GradeJpaRepository
) : GradePort {

    override fun findById(id: Int): Grade? {
        return gradeRepository.findById(id)
            .map { GradeMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(): List<Grade> {
        return gradeRepository.findAll()
            .map { GradeMapper.toDomain(it) }
    }

    override fun save(grade: Grade): Grade {
        val entity = GradeMapper.toEntity(grade)
        val savedEntity = gradeRepository.save(entity)
        return GradeMapper.toDomain(savedEntity)
    }

    override fun delete(id: Int) {
        gradeRepository.deleteById(id)
    }
}
