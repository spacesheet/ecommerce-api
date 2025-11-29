package com.ps.app.coupons.application.port.out

import com.ps.app.coupons.domain.Coupons
import com.ps.app.coupons.domain.CouponsId
import com.ps.app.user.domain.UserId

interface CouponsPort {
    fun findById(id: CouponsId): Coupons?
    fun findById(id: Long): Coupons?
    fun findByOwnerId(ownerId: UserId): List<Coupons>
    fun findByOwnerId(ownerId: Long): List<Coupons>
    fun findByCouponCode(couponCode: String): Coupons?
    fun save(coupons: Coupons): Coupons
    fun existsByCouponCode(couponCode: String): Boolean
    fun findAvailableCouponsByOwnerId(ownerId: UserId): List<Coupons> {
        return findByOwnerId(ownerId.value).filter { it.isAvailable() }
    }
}
