package com.ps.app.review.adapter.out.persistence

import com.ps.app.orders.adapter.out.persistence.OrderDetailJpaRepository
import com.ps.app.orders.domain.OrderDetailId
import com.ps.app.products.domain.ProductId
import com.ps.app.review.application.port.out.ReviewPort
import com.ps.app.review.domain.Review
import com.ps.app.review.domain.ReviewId
import com.ps.app.user.domain.UserId
import org.springframework.stereotype.Component


@Component
class ReviewPersistenceAdapter(
    private val reviewRepository: ReviewJpaRepository,
    private val orderDetailRepository: OrderDetailJpaRepository
) : ReviewPort {

    override fun save(review: Review): Review {
        val entity = if (review.isNew()) {
            val orderDetailEntity = orderDetailRepository.findById(review.orderDetail?.id)
                .orElseThrow { IllegalArgumentException("OrderDetail not found: ${review.orderDetail?.id}") }

            ReviewMapper.toEntity(review, orderDetailEntity)
        } else {
            val existing = reviewRepository.findById(review.id.value)
                .orElseThrow { IllegalArgumentException("Review not found: ${review.id}") }

            existing.apply {
                updateContent(review.content)
                updateScore(review.reviewScore)
                updatePicturePath(review.picturePath)
            }
        }

        val saved = reviewRepository.save(entity)
        return ReviewMapper.toDomain(saved)
    }

    override fun findById(id: ReviewId): Review? {
        return reviewRepository.findById(id.value)
            .map { ReviewMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByOrderDetailId(orderDetailId: OrderDetailId): Review? {
        return reviewRepository.findByOrderDetailId(orderDetailId.value)
            ?.let { ReviewMapper.toDomain(it) }
    }

    override fun findByProductId(productId: ProductId): List<Review> {
        return reviewRepository.findByProductIdWithDetails(productId.value)
            .map { ReviewMapper.toDomain(it) }
    }

    override fun findByUserId(userId: UserId): List<Review> {
        return reviewRepository.findByUserIdWithDetails(userId.value)
            .map { ReviewMapper.toDomain(it) }
    }

    override fun existsByOrderDetailId(orderDetailId: OrderDetailId): Boolean {
        return reviewRepository.existsByOrderDetailId(orderDetailId.value)
    }

    override fun delete(review: Review) {
        reviewRepository.deleteById(review.id.value)
    }

    override fun deleteById(id: ReviewId) {
        reviewRepository.deleteById(id.value)
    }

    override fun findByProductIdOrderByCreatedAtDesc(productId: ProductId): List<Review> {
        return reviewRepository.findByProductIdWithDetails(productId.value)
            .map { ReviewMapper.toDomain(it) }
    }

    override fun findPhotoReviewsByProductId(productId: ProductId): List<Review> {
        return reviewRepository.findPhotoReviewsByProductId(productId.value)
            .map { ReviewMapper.toDomain(it) }
    }
}
