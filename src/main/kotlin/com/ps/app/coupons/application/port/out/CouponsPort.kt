package com.ps.app.coupons.application.port.out

import com.ps.app.coupons.domain.Coupons

interface CouponsPort {
    fun findById(id: Long): Coupons?
    fun findByOwnerId(ownerId: Long): List<Coupons>
    fun findByCouponCode(couponCode: String): Coupons?
    fun save(coupons: Coupons): Coupons
    fun existsByCouponCode(couponCode: String): Boolean
}
