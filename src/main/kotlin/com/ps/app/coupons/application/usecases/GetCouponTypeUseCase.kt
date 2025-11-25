package com.ps.app.coupons.application.usecases

import com.ps.app.coupons.application.port.`in`.GetCouponTypeQuery
import com.ps.app.coupons.domain.CouponType

/**
 * 쿠폰 타입 조회 Use Case
 * Input Port
 */
interface GetCouponTypeUseCase {
    fun getCouponType(query: GetCouponTypeQuery): CouponType
    fun getAllCouponTypes(): List<CouponType>
}


