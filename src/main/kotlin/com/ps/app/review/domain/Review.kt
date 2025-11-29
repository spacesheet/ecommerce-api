package com.ps.app.review.domain

import com.ps.app.orders.domain.OrderDetail
import java.time.LocalDateTime

data class Review(
    val id: ReviewId = ReviewId.NEW,
    val content: String,
    val picturePath: String?,
    val reviewScore: Int,
    val reviewCreatedAt: LocalDateTime,
    val orderDetail: OrderDetail?
) {
    companion object {
        private const val MIN_SCORE = 1
        private const val MAX_SCORE = 5
        private const val MIN_CONTENT_LENGTH = 10
        private const val MAX_CONTENT_LENGTH = 1000

        fun create(
            content: String,
            picturePath: String?,
            reviewScore: Int,
            orderDetail: OrderDetail
        ): Review {
            validateContent(content)
            validateScore(reviewScore)

            return Review(
                id = ReviewId.NEW,
                content = content.trim(),
                picturePath = picturePath,
                reviewScore = reviewScore,
                reviewCreatedAt = LocalDateTime.now(),
                orderDetail = orderDetail
            )
        }

        private fun validateContent(content: String) {
            require(content.isNotBlank()) { "리뷰 내용은 비어있을 수 없습니다" }
            require(content.length >= MIN_CONTENT_LENGTH) {
                "리뷰 내용은 최소 ${MIN_CONTENT_LENGTH}자 이상이어야 합니다"
            }
            require(content.length <= MAX_CONTENT_LENGTH) {
                "리뷰 내용은 최대 ${MAX_CONTENT_LENGTH}자까지 가능합니다"
            }
        }

        private fun validateScore(score: Int) {
            require(score in MIN_SCORE..MAX_SCORE) {
                "리뷰 점수는 ${MIN_SCORE}~${MAX_SCORE} 사이여야 합니다"
            }
        }
    }

    init {
        validateContent(content)
        validateScore(reviewScore)
    }

    fun isNew(): Boolean = id.isNew()

    fun hasPhoto(): Boolean = !picturePath.isNullOrBlank()

    fun isPositive(): Boolean = reviewScore >= 4

    fun isNegative(): Boolean = reviewScore <= 2

    fun isNeutral(): Boolean = reviewScore == 3

    fun updateContent(newContent: String): Review {
        validateContent(newContent)
        return copy(content = newContent.trim())
    }

    fun updateScore(newScore: Int): Review {
        validateScore(newScore)
        return copy(reviewScore = newScore)
    }

    fun updatePhoto(newPicturePath: String?): Review {
        return copy(picturePath = newPicturePath)
    }

    fun removePhoto(): Review {
        return copy(picturePath = null)
    }

    fun belongsToUser(userId: Long): Boolean {
        return orderDetail.belongsToUser(userId)
    }

    fun getProductId(): Long {
        return orderDetail.product.id.value
    }

    fun isReviewForProduct(productId: Long): Boolean {
        return getProductId() == productId
    }
}
