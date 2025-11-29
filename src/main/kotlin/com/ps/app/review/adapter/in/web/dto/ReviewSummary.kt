package com.ps.app.review.adapter.`in`.web.dto

data class ReviewSummary(
    val totalReviews: Int,
    val averageScore: Double,
    val scoreDistribution: Map<Int, Int>,
    val photoReviewCount: Int
)
