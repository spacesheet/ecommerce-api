package com.ps.app.coupon.application.usecases

import com.ps.app.coupon.application.port.`in`.GetCouponTypeQuery
import com.ps.app.coupon.domain.CouponType
import com.ps.app.coupon.domain.constant.CouponScope

/**
 * 쿠폰 타입 조회 Use Case
 * Input Port
 */
interface GetCouponTypeUseCase {
    fun getCouponType(query: GetCouponTypeQuery): CouponType
    fun getAllCouponTypes(): List<CouponType>
}


