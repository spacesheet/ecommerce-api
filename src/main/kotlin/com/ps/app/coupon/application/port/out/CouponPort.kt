package com.ps.app.coupon.application.port.out

import com.ps.app.coupon.domain.Coupon

interface CouponPort {
    fun findById(id: Long): Coupon?
    fun findByOwnerId(ownerId: Long): List<Coupon>
    fun findByCouponCode(couponCode: String): Coupon?
    fun save(coupon: Coupon): Coupon
    fun existsByCouponCode(couponCode: String): Boolean
}
