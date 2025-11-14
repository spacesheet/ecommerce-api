package com.ps.app.user.adapter.`in`.web.dto

import java.time.LocalDate

/**
 * 사용자 정보 응답 DTO
 */
data class UserInfoResponse(
    val id: Long?,
    val name: String,
    val loginId: String,
    val birthday: LocalDate,
    val isAdmin: Boolean,
//    val grade: GradeResponse,
    val contactNumber: String,
    val email: String,
    val point: Int
)
