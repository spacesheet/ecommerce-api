package com.ps.app.review.application

import com.ps.app.orders.application.port.out.OrderDetailPort
import com.ps.app.orders.domain.OrderDetailId
import com.ps.app.products.domain.ProductId
import com.ps.app.review.adapter.`in`.web.dto.CreateReviewRequest
import com.ps.app.review.adapter.`in`.web.dto.ProductReviewResponse
import com.ps.app.review.adapter.`in`.web.dto.ReviewResponse
import com.ps.app.review.adapter.`in`.web.dto.ReviewSummary
import com.ps.app.review.adapter.out.persistence.ReviewMapper
import com.ps.app.review.application.port.out.ReviewPort
import com.ps.app.review.domain.Review
import com.ps.app.review.domain.ReviewId
import com.ps.app.user.domain.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.collections.first

@Service
@Transactional(readOnly = true)
private class ReviewService(
    private val reviewPort: ReviewPort,
    private val orderDetailPort: OrderDetailPort
) {

    @Transactional
    fun createReview(request: CreateReviewRequest, userId: Long): ReviewResponse {
        val orderDetailId = OrderDetailId(request.orderDetailId)

        // 이미 리뷰가 작성되었는지 확인
        if (reviewPort.existsByOrderDetailId(orderDetailId)) {
            throw IllegalStateException("이미 리뷰가 작성된 주문입니다")
        }

        val orderDetail = orderDetailPort.findById(orderDetailId)
            ?: throw IllegalArgumentException("주문 상세를 찾을 수 없습니다: ${request.orderDetailId}")

        // 주문한 사용자인지 확인
        if (!orderDetail.belongsToUser(userId)) {
            throw IllegalArgumentException("본인의 주문에만 리뷰를 작성할 수 있습니다")
        }

        // 주문이 배송 완료 상태인지 확인
        if (!orderDetail.isDelivered()) {
            throw IllegalStateException("배송 완료된 주문에만 리뷰를 작성할 수 있습니다")
        }

        val review = Review.create(
            content = request.content,
            picturePath = request.picturePath,
            reviewScore = request.reviewScore,
            orderDetail = orderDetail
        )

        val saved = reviewPort.save(review)
        return ReviewMapper.toResponse(saved)
    }

    fun getReview(reviewId: Int): ReviewResponse {
        val review = reviewPort.findById(ReviewId(reviewId))
            ?: throw IllegalArgumentException("리뷰를 찾을 수 없습니다: $reviewId")

        return ReviewMapper.toResponse(review)
    }

    fun getProductReviews(productId: Long): ProductReviewResponse {
        val reviews = reviewPort.findByProductIdOrderByCreatedAtDesc(ProductId(productId))

        if (reviews.isEmpty()) {
            // 상품이 존재하는지 확인하고 기본 응답 반환
            return ProductReviewResponse(
                productId = productId,
                productName = "", // 상품 정보를 가져와서 설정
                reviews = emptyList(),
                summary = ReviewSummary(0, 0.0, (1..5).associateWith { 0 }, 0)
            )
        }

        val productName = reviews.first().orderDetail.product.productName

        return ProductReviewResponse(
            productId = productId,
            productName = productName,
            reviews = ReviewMapper.toResponseList(reviews),
            summary = ReviewMapper.toSummary(reviews)
        )
    }

    fun getUserReviews(userId: Long): List<ReviewResponse> {
        val reviews = reviewPort.findByUserId(UserId(userId))
        return ReviewMapper.toResponseList(reviews)
    }

    fun getPhotoReviews(productId: Long): List<ReviewResponse> {
        val reviews = reviewPort.findPhotoReviewsByProductId(ProductId(productId))
        return ReviewMapper.toResponseList(reviews)
    }

    @Transactional
    fun updateReview(reviewId: Int, request: UpdateReviewRequest, userId: Long): ReviewResponse {
        val review = reviewPort.findById(ReviewId(reviewId))
            ?: throw IllegalArgumentException("리뷰를 찾을 수 없습니다: $reviewId")

        // 작성자 본인인지 확인
        if (!review.belongsToUser(userId)) {
            throw IllegalArgumentException("본인의 리뷰만 수정할 수 있습니다")
        }

        var updated = review

        request.content?.let {
            updated = updated.updateContent(it)
        }

        request.reviewScore?.let {
            updated = updated.updateScore(it)
        }

        request.picturePath?.let {
            updated = updated.updatePhoto(it)
        }

        val saved = reviewPort.save(updated)
        return ReviewMapper.toResponse(saved)
    }

    @Transactional
    fun deleteReview(reviewId: Int, userId: Long) {
        val review = reviewPort.findById(ReviewId(reviewId))
            ?: throw IllegalArgumentException("리뷰를 찾을 수 없습니다: $reviewId")

        // 작성자 본인인지 확인
        if (!review.belongsToUser(userId)) {
            throw IllegalArgumentException("본인의 리뷰만 삭제할 수 있습니다")
        }

        reviewPort.delete(review)
    }

    fun canWriteReview(orderDetailId: Long, userId: Long): Boolean {
        val orderDetail = orderDetailPort.findById(OrderDetailId(orderDetailId))
            ?: return false

        return orderDetail.belongsToUser(userId) &&
                orderDetail.isDelivered() &&
                !reviewPort.existsByOrderDetailId(OrderDetailId(orderDetailId))
    }
}
