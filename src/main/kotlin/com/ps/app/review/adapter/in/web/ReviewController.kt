package com.ps.app.review.adapter.`in`.web


import com.ps.app.review.adapter.`in`.web.dto.CreateReviewRequest
import com.ps.app.review.adapter.`in`.web.dto.ProductReviewResponse
import com.ps.app.review.adapter.`in`.web.dto.ReviewResponse
import com.ps.app.review.adapter.`in`.web.dto.UpdateReviewRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reviews")
class ReviewController(
    private val reviewService: ReviewService
) {

    @PostMapping
    fun createReview(
        @RequestBody request: CreateReviewRequest,
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<ReviewResponse> {
        val response = reviewService.createReview(request, userId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{reviewId}")
    fun getReview(@PathVariable reviewId: Int): ResponseEntity<ReviewResponse> {
        val response = reviewService.getReview(reviewId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/products/{productId}")
    fun getProductReviews(
        @PathVariable productId: Long
    ): ResponseEntity<ProductReviewResponse> {
        val response = reviewService.getProductReviews(productId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/products/{productId}/photos")
    fun getPhotoReviews(
        @PathVariable productId: Long
    ): ResponseEntity<List<ReviewResponse>> {
        val reviews = reviewService.getPhotoReviews(productId)
        return ResponseEntity.ok(reviews)
    }

    @GetMapping("/users/{userId}")
    fun getUserReviews(@PathVariable userId: Long): ResponseEntity<List<ReviewResponse>> {
        val reviews = reviewService.getUserReviews(userId)
        return ResponseEntity.ok(reviews)
    }

    @PutMapping("/{reviewId}")
    fun updateReview(
        @PathVariable reviewId: Int,
        @RequestBody request: UpdateReviewRequest,
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<ReviewResponse> {
        val response = reviewService.updateReview(reviewId, request, userId)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{reviewId}")
    fun deleteReview(
        @PathVariable reviewId: Int,
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<Void> {
        reviewService.deleteReview(reviewId, userId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/order-details/{orderDetailId}/can-write")
    fun canWriteReview(
        @PathVariable orderDetailId: Long,
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<Boolean> {
        val canWrite = reviewService.canWriteReview(orderDetailId, userId)
        return ResponseEntity.ok(canWrite)
    }
}
