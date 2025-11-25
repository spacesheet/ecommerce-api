package com.ps.app.coupons.application.usecases

import com.ps.app.coupons.application.port.`in`.CreateCategoryCouponCommand
import com.ps.app.coupons.domain.CategoryCoupon

/**
 * 카테고리 쿠폰 생성 Use Case
 */
interface CreateCategoryCouponUseCase {
    fun createCategoryCoupon(command: CreateCategoryCouponCommand): CategoryCoupon
}
