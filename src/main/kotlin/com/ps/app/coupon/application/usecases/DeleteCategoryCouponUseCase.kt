package com.ps.app.coupon.application.usecases

import com.ps.app.coupon.application.port.`in`.DeleteCategoryCouponCommand

/**
 * 카테고리 쿠폰 삭제 Use Case
 */
interface DeleteCategoryCouponUseCase {
    fun deleteCategoryCoupon(command: DeleteCategoryCouponCommand)
}
