package com.ps.app.review.application.port.out

interface ReviewPort {
    fun save(review: Review): Review
    fun findById(id: ReviewId): Review?
    fun findByOrderDetailId(orderDetailId: OrderDetailId): Review?
    fun findByProductId(productId: ProductId): List<Review>
    fun findByUserId(userId: UserId): List<Review>
    fun existsByOrderDetailId(orderDetailId: OrderDetailId): Boolean
    fun delete(review: Review)
    fun deleteById(id: ReviewId)
    fun findByProductIdOrderByCreatedAtDesc(productId: ProductId): List<Review>
    fun findPhotoReviewsByProductId(productId: ProductId): List<Review>
}
