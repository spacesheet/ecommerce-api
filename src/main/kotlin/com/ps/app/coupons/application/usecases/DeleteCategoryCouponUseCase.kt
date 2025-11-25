package com.ps.app.coupons.application.usecases

import com.ps.app.coupons.application.port.`in`.DeleteCategoryCouponCommand

/**
 * 카테고리 쿠폰 삭제 Use Case
 */
interface DeleteCategoryCouponUseCase {
    fun deleteCategoryCoupon(command: DeleteCategoryCouponCommand)
}
