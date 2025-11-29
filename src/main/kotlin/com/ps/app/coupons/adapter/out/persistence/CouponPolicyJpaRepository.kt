package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.adapter.out.persistence.CouponPolicyEntity
import com.ps.app.coupons.domain.constant.DiscountType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface CouponPolicyJpaRepository : JpaRepository<CouponPolicyEntity, Int> {

    // 활성 쿠폰 정책 조회
    @Query("""
        SELECT cp FROM CouponPolicyEntity cp
        JOIN FETCH cp.couponType
        WHERE cp.deleted = false
        AND cp.startDate <= :now
        AND cp.endDate >= :now
        ORDER BY cp.startDate DESC
    """)
    fun findActivePolicies(@Param("now") now: LocalDate): List<CouponPolicyEntity>

    // 이름으로 조회 (삭제되지 않은 것만)
    @Query("""
        SELECT cp FROM CouponPolicyEntity cp
        JOIN FETCH cp.couponType
        WHERE cp.name = :name
        AND cp.deleted = false
    """)
    fun findByNameAndDeletedFalse(@Param("name") name: String): CouponPolicyEntity?

    // 쿠폰 타입으로 조회
    @Query("""
        SELECT cp FROM CouponPolicyEntity cp
        JOIN FETCH cp.couponType ct
        WHERE ct.id = :couponTypeId
        AND cp.deleted = false
        ORDER BY cp.startDate DESC
    """)
    fun findByCouponTypeId(@Param("couponTypeId") couponTypeId: Int): List<CouponPolicyEntity>

    // 할인 타입으로 조회
    @Query("""
        SELECT cp FROM CouponPolicyEntity cp
        JOIN FETCH cp.couponType
        WHERE cp.discountType = :discountType
        AND cp.deleted = false
        ORDER BY cp.startDate DESC
    """)
    fun findByDiscountType(@Param("discountType") discountType: DiscountType): List<CouponPolicyEntity>

    // 적용 가능한 정책 조회
    @Query("""
        SELECT cp FROM CouponPolicyEntity cp
        JOIN FETCH cp.couponType
        WHERE cp.deleted = false
        AND cp.startDate <= :now
        AND cp.endDate >= :now
        AND cp.standardPrice <= :orderAmount
        ORDER BY cp.discountRate DESC, cp.discountAmount DESC
    """)
    fun findApplicablePolicies(
        @Param("orderAmount") orderAmount: Int,
        @Param("now") now: LocalDate
    ): List<CouponPolicyEntity>

    // 만료 예정 정책 조회
    @Query("""
        SELECT cp FROM CouponPolicyEntity cp
        JOIN FETCH cp.couponType
        WHERE cp.deleted = false
        AND cp.endDate BETWEEN :now AND :expiryDate
        ORDER BY cp.endDate ASC
    """)
    fun findExpiringPolicies(
        @Param("now") now: LocalDate,
        @Param("expiryDate") expiryDate: LocalDate
    ): List<CouponPolicyEntity>

    // 만료된 정책 조회
    @Query("""
        SELECT cp FROM CouponPolicyEntity cp
        JOIN FETCH cp.couponType
        WHERE cp.deleted = false
        AND cp.endDate < :now
    """)
    fun findExpiredPolicies(@Param("now") now: LocalDate): List<CouponPolicyEntity>

    // 정책명으로 검색
    @Query("""
        SELECT cp FROM CouponPolicyEntity cp
        JOIN FETCH cp.couponType
        WHERE cp.name LIKE %:keyword%
        AND cp.deleted = false
        ORDER BY cp.startDate DESC
    """)
    fun searchByName(@Param("keyword") keyword: String): List<CouponPolicyEntity>

    // 정책명 중복 확인
    fun existsByNameAndDeletedFalse(name: String): Boolean

    // 활성 정책 개수
    @Query("""
        SELECT COUNT(cp) FROM CouponPolicyEntity cp
        WHERE cp.deleted = false
        AND cp.startDate <= :now
        AND cp.endDate >= :now
    """)
    fun countActivePolicies(@Param("now") now: LocalDate): Long
}
