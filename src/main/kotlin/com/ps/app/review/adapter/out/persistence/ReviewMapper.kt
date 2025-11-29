package com.ps.app.review.adapter.out.persistence

import com.ps.app.orders.adapter.out.persistence.OrderDetailEntity
import com.ps.app.orders.adapter.out.persistence.OrderDetailMapper
import com.ps.app.review.adapter.`in`.web.dto.ReviewResponse
import com.ps.app.review.domain.Review
import com.ps.app.review.domain.ReviewId


object ReviewMapper {
    fun toDomain(entity: ReviewEntity): Review {
        return Review(
            id = ReviewId(entity.id),
            content = entity.content,
            picturePath = entity.picturePath,
            reviewScore = entity.reviewScore,
            reviewCreatedAt = entity.reviewCreateAt,
            orderDetail = OrderDetailMapper.toDomain(entity.orderDetail)
        )
    }

    fun toEntity(domain: Review, orderDetailEntity: OrderDetailEntity): ReviewEntity {
        return ReviewEntity(
            id = domain.id.value,
            content = domain.content,
            picturePath = domain.picturePath,
            reviewScore = domain.reviewScore,
            reviewCreateAt = domain.reviewCreatedAt,
            orderDetail = orderDetailEntity
        )
    }

    fun toResponse(review: Review): ReviewResponse {
        return ReviewResponse(
            id = review.id.value,
            content = review.content,
            picturePath = review.picturePath,
            reviewScore = review.reviewScore,
            reviewCreatedAt = review.reviewCreatedAt,
            orderDetailId = review.orderDetail?.id?.value,
            productId = review.orderDetail?.product?.id?.value,
            productName = review.orderDetail?.product?.name,
            userId = review.orderDetail.order.user.id.value,
            userName = review.orderDetail.order.user.name,
            hasPhoto = review.hasPhoto()
        )
    }

    fun toResponseList(reviews: List<Review>): List<ReviewResponse> {
        return reviews.map { toResponse(it) }
    }

    fun toSummary(reviews: List<Review>): ReviewSummary {
        val totalReviews = reviews.size
        val averageScore = if (totalReviews > 0) {
            reviews.map { it.reviewScore }.average()
        } else {
            0.0
        }

        val scoreDistribution = reviews
            .groupBy { it.reviewScore }
            .mapValues { it.value.size }
            .toMutableMap()
            .apply {
                (1..5).forEach { score ->
                    putIfAbsent(score, 0)
                }
            }

        val photoReviewCount = reviews.count { it.hasPhoto() }

        return ReviewSummary(
            totalReviews = totalReviews,
            averageScore = averageScore,
            scoreDistribution = scoreDistribution,
            photoReviewCount = photoReviewCount
        )
    }
}
