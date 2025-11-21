package com.ps.app.coupon.application.port.out

import com.ps.app.coupon.domain.CouponPolicy

interface CouponPolicyPort {
    fun findById(id: Long): CouponPolicy?
    fun findAll(): List<CouponPolicy>
    fun findActivePolicies(): List<CouponPolicy>
    fun findByName(name: String): CouponPolicy?
    fun save(couponPolicy: CouponPolicy): CouponPolicy
    fun delete(id: Int)
    fun existsByName(name: String): Boolean
}
