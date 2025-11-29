package com.ps.app.review.adapter.`in`.web.dto

import java.time.LocalDateTime

data class ReviewResponse(
    val id: Int,
    val content: String,
    val picturePath: String?,
    val reviewScore: Int,
    val reviewCreateAt: LocalDateTime,
    val orderDetailId: Long?,
    val productId: Long?,
    val productName: String?,
    val userId: Long,
    val userName: String,
    val hasPhoto: Boolean
)

