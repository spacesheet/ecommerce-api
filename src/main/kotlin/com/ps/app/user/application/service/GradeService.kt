package com.ps.app.user.application.service

import com.ps.app.user.adapter.`in`.web.dto.GradeInfoResponse
import com.ps.app.user.application.port.out.GradePort
import com.ps.app.user.domain.Grade
import com.ps.app.user.domain.constant.GradeName
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GradeService(
    private val gradePort: GradePort
) {
    fun getGradeInfo(id: Int): GradeInfoResponse {
        val grade = gradePort.findById(id)
            ?: throw IllegalArgumentException("Grade not found with id: $id")

        return toResponse(grade)
    }

    fun getAllGrades(): List<GradeInfoResponse> {
        return gradePort.findAll()
            .map { toResponse(it) }
    }

    @Transactional
    fun createGrade(name: String, standard: Int, benefit: Double): GradeInfoResponse {
        val grade = Grade(
            name = GradeName.valueOf(name),
            standard = standard,
            benefit = benefit
        )

        val savedGrade = gradePort.save(grade)
        return toResponse(savedGrade)
    }

    private fun toResponse(grade: Grade): GradeInfoResponse {
        return GradeInfoResponse(
            name = grade.name.name,
            standard = grade.standard,
            benefit = grade.benefit
        )
    }
}
