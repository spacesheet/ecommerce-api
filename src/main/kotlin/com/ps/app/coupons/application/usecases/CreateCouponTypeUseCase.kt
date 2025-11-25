package com.ps.app.coupons.application.usecases

import com.ps.app.coupons.adapter.`in`.web.dto.CreateCouponTypeRequest
import com.ps.app.coupons.domain.CouponType

/**
 * 쿠폰 타입 생성 Use Case
 * Input Port
 */
interface CreateCouponTypeUseCase {
    fun createCouponType(command: CreateCouponTypeRequest): CouponType
}
