package com.ps.app.coupon.application.usecases

import com.ps.app.coupon.adapter.`in`.web.dto.CreateCouponTypeRequest
import com.ps.app.coupon.domain.CouponType

/**
 * 쿠폰 타입 생성 Use Case
 * Input Port
 */
interface CreateCouponTypeUseCase {
    fun createCouponType(command: CreateCouponTypeRequest): CouponType
}
