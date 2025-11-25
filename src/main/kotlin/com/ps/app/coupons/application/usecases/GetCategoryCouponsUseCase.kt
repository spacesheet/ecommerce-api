package com.ps.app.coupons.application.usecases

import com.ps.app.coupons.application.port.`in`.GetCategoryCouponsQuery
import com.ps.app.coupons.domain.CategoryCoupon

/**
 * 카테고리 쿠폰 조회 Use Case
 */
interface GetCategoryCouponsUseCase {
    fun getCategoryCoupons(query: GetCategoryCouponsQuery): List<CategoryCoupon>
}
