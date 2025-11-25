package com.ps.app.coupons.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface CouponTypeJpaRepository : JpaRepository<CouponTypeEntity, Int> {
    fun findByName(name: String): CouponTypeEntity?
    fun existsByName(name: String): Boolean
}
