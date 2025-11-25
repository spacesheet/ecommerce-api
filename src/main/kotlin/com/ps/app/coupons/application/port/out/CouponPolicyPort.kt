package com.ps.app.coupons.application.port.out

import com.ps.app.coupons.domain.CouponPolicy

interface CouponPolicyPort {
    fun findById(id: Int): CouponPolicy?
    fun findAll(): List<CouponPolicy>
    fun findActivePolicies(): List<CouponPolicy>
    fun findByName(name: String): CouponPolicy?
    fun save(couponPolicy: CouponPolicy): CouponPolicy
    fun delete(id: Int)
    fun existsByName(name: String): Boolean
}
