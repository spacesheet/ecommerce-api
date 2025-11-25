package com.ps.app.coupons.application.port.out

import com.ps.app.coupons.domain.CouponType
import com.ps.app.coupons.domain.constant.CouponScope

/**
 * 쿠폰 타입 Output Port
 * Secondary Port - 애플리케이션이 호출하는 인터페이스
 */
interface CouponTypePort {
    fun findById(id: Int): CouponType?
    fun findByName(name: CouponScope): CouponType?
    fun findAll(): List<CouponType>
    fun save(couponType: CouponType): CouponType
    fun delete(id: Int)
    fun existsByName(name: CouponScope): Boolean
}
