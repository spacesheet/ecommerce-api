package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.adapter.out.persistence.CouponsEntity
import com.ps.app.coupons.domain.constant.CouponStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface CouponsJpaRepository : JpaRepository<CouponsEntity, Long> {

    // ========== 쿠폰 코드 관련 ==========

    /**
     * 쿠폰 코드로 조회 (정책 정보 포함)
     */
    @Query("""
        SELECT c FROM CouponsEntity c
        JOIN FETCH c.couponPolicy cp
        JOIN FETCH cp.couponType ct
        WHERE c.couponCode = :couponCode
    """)
    fun findByCouponCode(@Param("couponCode") couponCode: String): CouponsEntity?

    /**
     * 쿠폰 코드 존재 여부
     */
    fun existsByCouponCode(couponCode: String): Boolean

    // ========== 소유자별 조회 ==========

    /**
     * 소유자의 모든 쿠폰 조회
     */
    @Query("""
        SELECT c FROM CouponsEntity c
        JOIN FETCH c.couponPolicy cp
        JOIN FETCH cp.couponType ct
        WHERE c.ownerId = :ownerId
        ORDER BY c.createDate DESC
    """)
    fun findByOwnerId(@Param("ownerId") ownerId: Long): List<CouponsEntity>

    /**
     * 소유자의 사용 가능한 쿠폰 조회
     */
    @Query("""
        SELECT c FROM CouponsEntity c
        JOIN FETCH c.couponPolicy cp
        JOIN FETCH cp.couponType ct
        WHERE c.ownerId = :ownerId
        AND c.couponStatus = 'AVAILABLE'
        AND c.expireDate >= :now
        AND cp.deleted = false
        AND cp.startDate <= :now
        AND cp.endDate >= :now
        ORDER BY c.expireDate ASC
    """)
    fun findAvailableCouponsByOwnerId(
        @Param("ownerId") ownerId: Long,
        @Param("now") now: LocalDate
    ): List<CouponsEntity>

    /**
     * 소유자의 사용된 쿠폰 조회
     */
    @Query("""
        SELECT c FROM CouponsEntity c
        JOIN FETCH c.couponPolicy cp
        JOIN FETCH cp.couponType ct
        WHERE c.ownerId = :ownerId
        AND c.couponStatus = 'USED'
        ORDER BY c.createDate DESC
    """)
    fun findUsedCouponsByOwnerId(@Param("ownerId") ownerId: Long): List<CouponsEntity>

    /**
     * 소유자의 만료된 쿠폰 조회
     */
    @Query("""
        SELECT c FROM CouponsEntity c
        JOIN FETCH c.couponPolicy cp
        JOIN FETCH cp.couponType ct
        WHERE c.ownerId = :ownerId
        AND (c.couponStatus = 'EXPIRED' OR c.expireDate < :now)
        ORDER BY c.expireDate DESC
    """)
    fun findExpiredCouponsByOwnerId(
        @Param("ownerId") ownerId: Long,
        @Param("now") now: LocalDate
    ): List<CouponsEntity>

    // ========== 정책별 조회 ==========

    /**
     * 쿠폰 정책별 발급된 쿠폰 조회
     */
    @Query("""
        SELECT c FROM CouponsEntity c
        JOIN FETCH c.couponPolicy cp
        WHERE cp.id = :policyId
        ORDER BY c.createDate DESC
    """)
    fun findByCouponPolicyId(@Param("policyId") policyId: Int): List<CouponsEntity>

    /**
     * 쿠폰 정책별 사용 가능한 쿠폰 개수
     */
    @Query("""
        SELECT COUNT(c) FROM CouponsEntity c
        WHERE c.couponPolicy.id = :policyId
        AND c.couponStatus = 'AVAILABLE'
        AND c.expireDate >= :now
    """)
    fun countAvailableCouponsByPolicyId(
        @Param("policyId") policyId: Int,
        @Param("now") now: LocalDate
    ): Long

    /**
     * 쿠폰 정책별 사용된 쿠폰 개수
     */
    @Query("""
        SELECT COUNT(c) FROM CouponsEntity c
        WHERE c.couponPolicy.id = :policyId
        AND c.couponStatus = 'USED'
    """)
    fun countUsedCouponsByPolicyId(@Param("policyId") policyId: Int): Long

    // ========== 상태별 조회 ==========

    /**
     * 상태별 쿠폰 조회
     */
    @Query("""
        SELECT c FROM CouponsEntity c
        JOIN FETCH c.couponPolicy cp
        JOIN FETCH cp.couponType ct
        WHERE c.couponStatus = :status
        ORDER BY c.createDate DESC
    """)
    fun findByStatus(@Param("status") status: CouponStatus): List<CouponsEntity>

    /**
     * 만료 예정 쿠폰 조회 (N일 이내)
     */
    @Query("""
        SELECT c FROM CouponsEntity c
        JOIN FETCH c.couponPolicy cp
        JOIN FETCH cp.couponType ct
        WHERE c.couponStatus = 'AVAILABLE'
        AND c.expireDate BETWEEN :now AND :expiryDate
        ORDER BY c.expireDate ASC
    """)
    fun findExpiringCoupons(
        @Param("now") now: LocalDate,
        @Param("expiryDate") expiryDate: LocalDate
    ): List<CouponsEntity>

    /**
     * 소유자의 만료 예정 쿠폰 조회
     */
    @Query("""
        SELECT c FROM CouponsEntity c
        JOIN FETCH c.couponPolicy cp
        JOIN FETCH cp.couponType ct
        WHERE c.ownerId = :ownerId
        AND c.couponStatus = 'AVAILABLE'
        AND c.expireDate BETWEEN :now AND :expiryDate
        ORDER BY c.expireDate ASC
    """)
    fun findExpiringCouponsByOwnerId(
        @Param("ownerId") ownerId: Long,
        @Param("now") now: LocalDate,
        @Param("expiryDate") expiryDate: LocalDate
    ): List<CouponsEntity>

    /**
     * 자동 만료 처리할 쿠폰 조회
     */
    @Query("""
        SELECT c FROM CouponsEntity c
        WHERE c.couponStatus = 'AVAILABLE'
        AND c.expireDate < :now
    """)
    fun findCouponsToExpire(@Param("now") now: LocalDate): List<CouponsEntity>

    // ========== 주문 적용 관련 ==========

    /**
     * 주문에 적용 가능한 쿠폰 조회 (주문 금액 기준)
     */
    @Query("""
        SELECT c FROM CouponsEntity c
        JOIN FETCH c.couponPolicy cp
        JOIN FETCH cp.couponType ct
        WHERE c.ownerId = :ownerId
        AND c.couponStatus = 'AVAILABLE'
        AND c.expireDate >= :now
        AND cp.standardPrice <= :orderAmount
        AND cp.deleted = false
        AND cp.startDate <= :now
        AND cp.endDate >= :now
        ORDER BY cp.discountRate DESC, cp.discountAmount DESC
    """)
    fun findApplicableCouponsByOwnerIdAndOrderAmount(
        @Param("ownerId") ownerId: Long,
        @Param("orderAmount") orderAmount: Int,
        @Param("now") now: LocalDate
    ): List<CouponsEntity>

    /**
     * 특정 쿠폰이 주문에 적용 가능한지 확인
     */
    @Query("""
        SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END
        FROM CouponsEntity c
        JOIN c.couponPolicy cp
        WHERE c.couponCode = :couponCode
        AND c.ownerId = :ownerId
        AND c.couponStatus = 'AVAILABLE'
        AND c.expireDate >= :now
        AND cp.standardPrice <= :orderAmount
        AND cp.deleted = false
        AND cp.startDate <= :now
        AND cp.endDate >= :now
    """)
    fun isCouponApplicableToOrder(
        @Param("couponCode") couponCode: String,
        @Param("ownerId") ownerId: Long,
        @Param("orderAmount") orderAmount: Int,
        @Param("now") now: LocalDate
    ): Boolean

    // ========== 쿠폰 타입별 조회 ==========

    /**
     * 전체 쿠폰(GLOBAL) 조회
     */
    @Query("""
        SELECT c FROM CouponsEntity c
        JOIN FETCH c.couponPolicy cp
        JOIN FETCH cp.couponType ct
        WHERE ct.name = 'GLOBAL'
        AND c.ownerId = :ownerId
        AND c.couponStatus = 'AVAILABLE'
        ORDER BY c.expireDate ASC
    """)
    fun findGlobalCouponsByOwnerId(@Param("ownerId") ownerId: Long): List<CouponsEntity>

    /**
     * 특정 쿠폰(SPECIFIC) 조회
     */
    @Query("""
        SELECT c FROM CouponsEntity c
        JOIN FETCH c.couponPolicy cp
        JOIN FETCH cp.couponType ct
        WHERE ct.name = 'SPECIFIC'
        AND c.ownerId = :ownerId
        AND c.couponStatus = 'AVAILABLE'
        ORDER BY c.expireDate ASC
    """)
    fun findSpecificCouponsByOwnerId(@Param("ownerId") ownerId: Long): List<CouponsEntity>

    // ========== 통계 및 집계 ==========

    /**
     * 소유자의 쿠폰 개수
     */
    fun countByOwnerId(ownerId: Long): Long

    /**
     * 소유자의 사용 가능한 쿠폰 개수
     */
    @Query("""
        SELECT COUNT(c) FROM CouponsEntity c
        WHERE c.ownerId = :ownerId
        AND c.couponStatus = 'AVAILABLE'
        AND c.expireDate >= :now
    """)
    fun countAvailableCouponsByOwnerId(
        @Param("ownerId") ownerId: Long,
        @Param("now") now: LocalDate
    ): Long

    /**
     * 소유자의 사용된 쿠폰 개수
     */
    @Query("""
        SELECT COUNT(c) FROM CouponsEntity c
        WHERE c.ownerId = :ownerId
        AND c.couponStatus = 'USED'
    """)
    fun countUsedCouponsByOwnerId(@Param("ownerId") ownerId: Long): Long

    /**
     * 소유자의 만료된 쿠폰 개수
     */
    @Query("""
        SELECT COUNT(c) FROM CouponsEntity c
        WHERE c.ownerId = :ownerId
        AND (c.couponStatus = 'EXPIRED' OR c.expireDate < :now)
    """)
    fun countExpiredCouponsByOwnerId(
        @Param("ownerId") ownerId: Long,
        @Param("now") now: LocalDate
    ): Long

    /**
     * 상태별 쿠폰 개수
     */
    fun countByStatus(status: CouponStatus): Long

    /**
     * 전체 발급된 쿠폰 개수
     */
    @Query("""
        SELECT COUNT(c) FROM CouponsEntity c
        WHERE c.createDate BETWEEN :startDate AND :endDate
    """)
    fun countCouponsIssuedBetween(
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): Long

    /**
     * 전체 사용된 쿠폰 개수
     */
    @Query("""
        SELECT COUNT(c) FROM CouponsEntity c
        WHERE c.couponStatus = 'USED'
        AND c.createDate BETWEEN :startDate AND :endDate
    """)
    fun countCouponsUsedBetween(
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): Long

    // ========== 기간별 조회 ==========

    /**
     * 특정 기간 내 발급된 쿠폰 조회
     */
    @Query("""
        SELECT c FROM CouponsEntity c
        JOIN FETCH c.couponPolicy cp
        WHERE c.createDate BETWEEN :startDate AND :endDate
        ORDER BY c.createDate DESC
    """)
    fun findCouponsIssuedBetween(
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): List<CouponsEntity>

    /**
     * 특정 기간 내 사용된 쿠폰 조회
     */
    @Query("""
        SELECT c FROM CouponsEntity c
        JOIN FETCH c.couponPolicy cp
        WHERE c.couponStatus = 'USED'
        AND c.createDate BETWEEN :startDate AND :endDate
        ORDER BY c.createDate DESC
    """)
    fun findCouponsUsedBetween(
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): List<CouponsEntity>

    // ========== 중복 발급 방지 ==========

    /**
     * 특정 사용자가 특정 정책의 쿠폰을 이미 보유하고 있는지 확인
     */
    @Query("""
        SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END
        FROM CouponsEntity c
        WHERE c.ownerId = :ownerId
        AND c.couponPolicy.id = :policyId
        AND c.couponStatus = 'AVAILABLE'
    """)
    fun existsByOwnerIdAndPolicyId(
        @Param("ownerId") ownerId: Long,
        @Param("policyId") policyId: Int
    ): Boolean

    /**
     * 사용자가 보유한 특정 정책의 쿠폰 개수
     */
    @Query("""
        SELECT COUNT(c) FROM CouponsEntity c
        WHERE c.ownerId = :ownerId
        AND c.couponPolicy.id = :policyId
        AND c.couponStatus IN ('AVAILABLE', 'USED')
    """)
    fun countByOwnerIdAndPolicyId(
        @Param("ownerId") ownerId: Long,
        @Param("policyId") policyId: Int
    ): Long
}
