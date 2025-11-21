package com.ps.app.coupon.application.usecases

import com.ps.app.coupon.application.port.`in`.CreateCategoryCouponCommand
import com.ps.app.coupon.domain.CategoryCoupon

/**
 * 카테고리 쿠폰 생성 Use Case
 */
interface CreateCategoryCouponUseCase {
    fun createCategoryCoupon(command: CreateCategoryCouponCommand): CategoryCoupon
}
