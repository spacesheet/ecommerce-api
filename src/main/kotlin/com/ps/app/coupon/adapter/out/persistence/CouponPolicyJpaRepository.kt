package com.ps.app.coupon.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface CouponPolicyJpaRepository : JpaRepository<CouponPolicyEntity, Int> {
    fun findByName(name: String): CouponPolicyEntity?
    fun existsByName(name: String): Boolean
    fun findByDeletedFalseAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        startDate: LocalDate,
        endDate: LocalDate
    ): List<CouponPolicyEntity>
    fun findByDeletedFalse(): List<CouponPolicyEntity>
}
