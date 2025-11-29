package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.domain.CategoryCoupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * 카테고리 쿠폰 JPA Repository
 */
@Repository
interface CategoryCouponJpaRepository : JpaRepository<CategoryCouponEntity, Int> {

    /**
     * 쿠폰 정책 ID로 카테고리 쿠폰 조회
     */
    fun findByCouponPolicyId(couponPolicyId: Int): List<CategoryCouponEntity>

    /**
     * 카테고리 ID로 카테고리 쿠폰 조회
     */
    fun findByCategoryId(categoryId: Int): List<CategoryCouponEntity>

    /**
     * 쿠폰 정책 ID와 카테고리 ID로 존재 여부 확인
     */
    fun existsByCouponPolicyIdAndCategoryId(couponPolicyId: Int, categoryId: Int): Boolean

    /**
     * 쿠폰 정책 ID와 카테고리 ID로 조회
     */
    fun findByCouponPolicyIdAndCategoryId(couponPolicyId: Int, categoryId: Int): CategoryCouponEntity?

    /**
     * 여러 카테고리 ID로 카테고리 쿠폰 조회
     */
    fun findByCategoryIdIn(categoryIds: List<Int>): List<CategoryCouponEntity>

    /**
     * 쿠폰 정책 ID로 삭제
     */
    fun deleteByCouponPolicyId(couponPolicyId: Int): Int

    /**
     * 카테고리 ID로 삭제
     */
    fun deleteByCategoryId(categoryId: Int): Int

    /**
     * 쿠폰 정책 ID로 카테고리 쿠폰 개수 조회
     */
    fun countByCouponPolicyId(couponPolicyId: Int): Long

    /**
     * 카테고리 ID로 카테고리 쿠폰 개수 조회
     */
    fun countByCategoryId(categoryId: Int): Long

    /**
     * 쿠폰 정책 ID 목록으로 카테고리 쿠폰 조회 (JPQL 사용 예시)
     */
    @Query("SELECT cc FROM CategoryCouponEntity cc WHERE cc.couponPolicy.id IN :policyIds")
    fun findByPolicyIds(@Param("policyIds") policyIds: List<Int>): List<CategoryCouponEntity>

    /**
     * 카테고리 ID와 쿠폰 정책 활성 상태로 조회 (복잡한 쿼리 예시)
     */
    @Query("""
        SELECT cc FROM CategoryCouponEntity cc 
        JOIN cc.couponPolicy cp 
        WHERE cc.categoryId = :categoryId 
        AND cp.deleted = false 
        AND cp.startDate <= CURRENT_DATE 
        AND cp.endDate >= CURRENT_DATE
    """)
    fun findActiveCouponsByCategoryId(@Param("categoryId") categoryId: Int): List<CategoryCouponEntity>
}
