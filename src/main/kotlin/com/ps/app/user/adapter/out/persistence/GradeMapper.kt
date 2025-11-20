package com.ps.app.user.adapter.out.persistence

import com.ps.app.user.domain.Grade
import com.ps.app.user.domain.constant.GradeName

object GradeMapper {
    fun toDomain(entity: GradeEntity): Grade {
        return Grade(
            id = entity.id,
            name = GradeName.valueOf(entity.name),
            standard = entity.standard,
            benefit = entity.benefit
        )
    }

    fun toEntity(domain: Grade): GradeEntity {
        return GradeEntity(
            id = domain.id,
            name = domain.name,
            standard = domain.standard,
            benefit = domain.benefit
        )
    }
}
