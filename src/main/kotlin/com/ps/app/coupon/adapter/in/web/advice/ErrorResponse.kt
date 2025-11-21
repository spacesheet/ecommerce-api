package com.ps.app.coupon.adapter.`in`.web.advice

import java.time.LocalDateTime

/**
 * 에러 응답 DTO
 */
data class ErrorResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String,
    val path: String
)
