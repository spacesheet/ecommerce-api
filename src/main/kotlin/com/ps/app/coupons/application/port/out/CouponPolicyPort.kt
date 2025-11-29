package com.ps.app.coupons.application.port.out

import com.ps.app.coupons.domain.CouponPolicy
import com.ps.app.coupons.domain.CouponPolicyId
import com.ps.app.coupons.domain.CouponTypeId
import com.ps.app.coupons.domain.constant.DiscountType

interface CouponPolicyPort {
    // 기본 CRUD
    fun save(couponPolicy: CouponPolicy): CouponPolicy
    fun findById(id: CouponPolicyId): CouponPolicy?
    fun findById(id: Int): CouponPolicy?
    fun findAll(): List<CouponPolicy>
    fun delete(couponPolicy: CouponPolicy)
    fun delete(id: Int)

    // 이름 기반 조회
    fun findByName(name: String): CouponPolicy?
    fun searchByName(keyword: String): List<CouponPolicy>
    fun existsByName(name: String): Boolean

    // 상태 기반 조회
    fun findActivePolicies(): List<CouponPolicy>
    fun findExpiredPolicies(): List<CouponPolicy>
    fun findExpiringPolicies(days: Int): List<CouponPolicy>

    // 타입 기반 조회
    fun findByCouponTypeId(couponTypeId: CouponTypeId): List<CouponPolicy>
    fun findByDiscountType(discountType: DiscountType): List<CouponPolicy>

    // 비즈니스 로직 기반 조회
    fun findApplicablePolicies(orderAmount: Int): List<CouponPolicy>
}
