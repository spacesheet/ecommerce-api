package com.ps.app.coupons.application.port.out


import com.ps.app.coupons.domain.CategoryCoupon

/**
 * 카테고리 쿠폰 Output Port
 * Secondary Port
 */
interface CategoryCouponPort {
    fun findById(id: Int): CategoryCoupon?
    fun findByCouponPolicyId(couponPolicyId: Int): List<CategoryCoupon>
    fun findByCategoryId(categoryId: Int): List<CategoryCoupon>
    fun findAll(): List<CategoryCoupon>
    fun save(categoryCoupon: CategoryCoupon): CategoryCoupon
    fun delete(id: Int)
    fun existsByCouponPolicyIdAndCategoryId(couponPolicyId: Int, categoryId: Int): Boolean
}
