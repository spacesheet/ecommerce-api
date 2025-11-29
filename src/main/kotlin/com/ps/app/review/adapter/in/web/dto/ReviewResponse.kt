package com.ps.app.review.adapter.`in`.web.dto

data class ReviewResponse(
    val id: Int,
    val content: String,
    val picturePath: String?,
    val reviewScore: Int,
    val reviewCreatedAt: LocalDateTime,
    val orderDetailId: Long?,
    val productId: Long?,
    val productName: String?,
    val userId: Long,
    val userName: String,
    val hasPhoto: Boolean
)

