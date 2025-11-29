package com.ps.app.review.adapter.`in`.web.dto

data class CreateReviewRequest(
    val orderDetailId: Long,
    val content: String,
    val picturePath: String?,
    val reviewScore: Int
)
