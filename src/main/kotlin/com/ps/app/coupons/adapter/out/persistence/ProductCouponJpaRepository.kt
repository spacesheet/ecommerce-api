package com.ps.app.coupons.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProductCouponJpaRepository : JpaRepository<ProductCouponEntity, Int> {

    /**
     * 쿠폰 정책 ID로 상품 쿠폰 조회
     */
    @Query("""
        SELECT pc FROM ProductCouponEntity pc
        JOIN FETCH pc.couponPolicy cp
        JOIN FETCH cp.couponType
        WHERE cp.id = :policyId
    """)
    fun findByCouponPolicyId(@Param("policyId") policyId: Int): List<ProductCouponEntity>

    /**
     * 상품 ID로 적용 가능한 쿠폰 정책 조회
     */
    @Query("""
        SELECT pc FROM ProductCouponEntity pc
        JOIN FETCH pc.couponPolicy cp
        JOIN FETCH cp.couponType
        WHERE pc.productId = :productId
        AND cp.deleted = false
        AND cp.isActive = true
    """)
    fun findByProductId(@Param("productId") productId: Long): List<ProductCouponEntity>

    /**
     * 정책 ID와 상품 ID로 조회
     */
    @Query("""
        SELECT pc FROM ProductCouponEntity pc
        JOIN FETCH pc.couponPolicy cp
        WHERE cp.id = :policyId
        AND pc.productId = :productId
    """)
    fun findByCouponPolicyIdAndProductId(
        @Param("policyId") policyId: Int,
        @Param("productId") productId: Long
    ): ProductCouponEntity?

    /**
     * 중복 확인
     */
    @Query("""
        SELECT CASE WHEN COUNT(pc) > 0 THEN true ELSE false END
        FROM ProductCouponEntity pc
        WHERE pc.couponPolicy.id = :policyId
        AND pc.productId = :productId
    """)
    fun existsByCouponPolicyIdAndProductId(
        @Param("policyId") policyId: Int,
        @Param("productId") productId: Long
    ): Boolean

    /**
     * 활성화된 정책의 상품 쿠폰만 조회
     */
    @Query("""
        SELECT pc FROM ProductCouponEntity pc
        JOIN FETCH pc.couponPolicy cp
        JOIN FETCH cp.couponType
        WHERE pc.productId = :productId
        AND cp.deleted = false
        AND cp.startDate <= CURRENT_DATE
        AND cp.endDate >= CURRENT_DATE
    """)
    fun findActiveByProductId(@Param("productId") productId: Long): List<ProductCouponEntity>

    /**
     * 특정 정책의 상품 쿠폰 개수
     */
    @Query("""
        SELECT COUNT(pc) FROM ProductCouponEntity pc
        WHERE pc.couponPolicy.id = :policyId
    """)
    fun countByCouponPolicyId(@Param("policyId") policyId: Int): Long

    /**
     * 특정 상품에 적용 가능한 쿠폰 정책 개수
     */
    @Query("""
        SELECT COUNT(pc) FROM ProductCouponEntity pc
        JOIN pc.couponPolicy cp
        WHERE pc.productId = :productId
        AND cp.deleted = false
    """)
    fun countByProductId(@Param("productId") productId: Long): Long
}
