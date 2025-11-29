package com.ps.app.review.adapter.`in`.web.dto

data class ProductReviewResponse(
    val productId: Long,
    val productName: String,
    val reviews: List<ReviewResponse>,
    val summary: ReviewSummary
)
