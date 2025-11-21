package com.ps.app.coupon.application.usecases

import com.ps.app.coupon.application.port.`in`.GetCategoryCouponsQuery
import com.ps.app.coupon.domain.CategoryCoupon

/**
 * 카테고리 쿠폰 조회 Use Case
 */
interface GetCategoryCouponsUseCase {
    fun getCategoryCoupons(query: GetCategoryCouponsQuery): List<CategoryCoupon>
}
