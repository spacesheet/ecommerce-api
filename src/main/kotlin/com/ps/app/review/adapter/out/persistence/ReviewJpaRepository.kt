package com.ps.app.review.adapter.out.persistence


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ReviewJpaRepository : JpaRepository<ReviewEntity, Int> {

    fun findByOrderDetailId(orderDetailId: Long): ReviewEntity?

    fun existsByOrderDetailId(orderDetailId: Long): Boolean

    @Query("""
        SELECT r FROM ReviewEntity r
        JOIN FETCH r.orderDetail od
        JOIN FETCH od.product p
        JOIN FETCH od.order o
        JOIN FETCH o.user
        WHERE p.id = :productId
        ORDER BY r.reviewCreatedAt DESC
    """)
    fun findByProductIdWithDetails(@Param("productId") productId: Long): List<ReviewEntity>

    @Query("""
        SELECT r FROM ReviewEntity r
        JOIN FETCH r.orderDetail od
        JOIN FETCH od.order o
        WHERE o.user.id = :userId
        ORDER BY r.reviewCreatedAt DESC
    """)
    fun findByUserIdWithDetails(@Param("userId") userId: Long): List<ReviewEntity>

    @Query("""
        SELECT r FROM ReviewEntity r
        JOIN FETCH r.orderDetail od
        JOIN FETCH od.product p
        WHERE p.id = :productId AND r.picturePath IS NOT NULL
        ORDER BY r.reviewCreatedAt DESC
    """)
    fun findPhotoReviewsByProductId(@Param("productId") productId: Long): List<ReviewEntity>

    @Query("""
        SELECT AVG(r.reviewScore) FROM ReviewEntity r
        JOIN r.orderDetail od
        JOIN od.product p
        WHERE p.id = :productId
    """)
    fun getAverageScoreByProductId(@Param("productId") productId: Long): Double?

    @Query("""
        SELECT COUNT(r) FROM ReviewEntity r
        JOIN r.orderDetail od
        JOIN od.product p
        WHERE p.id = :productId
    """)
    fun countByProductId(@Param("productId") productId: Long): Long
}
