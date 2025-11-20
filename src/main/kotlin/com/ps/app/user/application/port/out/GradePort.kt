package com.ps.app.user.application.port.out

import com.ps.app.user.domain.Grade

interface GradePort {
    fun findById(id: Int): Grade?
    fun findAll(): List<Grade>
    fun save(grade: Grade): Grade
    fun delete(id: Int)
}
