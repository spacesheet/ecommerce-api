package com.ps.app.user.domain

import com.ps.app.user.domain.Grade
import java.time.LocalDate

/**
 * 사용자 정보 읽기 전용 Value Object
 * Domain 모델을 포함
 */
data class UserInfo(
    val userId: Long?,
    val name: String,
    val loginId: String,
    val birthday: LocalDate,
    val isAdmin: Boolean,
    val grade: Grade,  // Grade 도메인 모델
    val contactNumber: String,
    val email: String,
    val point: Int
)
