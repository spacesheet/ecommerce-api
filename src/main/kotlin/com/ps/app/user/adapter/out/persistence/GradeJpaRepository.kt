package com.ps.app.user.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface GradeJpaRepository : JpaRepository<GradeEntity, Int>
