package com.ps.app.cart.adapter.out.persistence


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface WishlistJpaRepository : JpaRepository<WishlistEntity, Long> {

    fun findByUserId(userId: Long): List<WishlistEntity>

    fun findByUserIdAndProductId(userId: Long, productId: Long): WishlistEntity?

    fun existsByUserIdAndProductId(userId: Long, productId: Long): Boolean

    @Query("""
        SELECT w FROM WishlistEntity w 
        JOIN FETCH w.product p 
        JOIN FETCH p.category 
        LEFT JOIN FETCH p.productTags pt 
        LEFT JOIN FETCH pt.tag 
        WHERE w.user.id = :userId
    """)
    fun findByUserIdWithProduct(@Param("userId") userId: Long): List<WishlistEntity>

    fun deleteByUserIdAndProductId(userId: Long, productId: Long): Int
}
