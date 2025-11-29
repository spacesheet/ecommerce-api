package com.ps.app.coupons.application.port.out

import com.ps.app.coupons.domain.CategoryCoupon
import com.ps.app.coupons.domain.CategoryCouponId
import com.ps.app.coupons.domain.CouponPolicyId
import com.ps.app.products.domain.CategoryId

interface CategoryCouponPort {
    fun save(categoryCoupon: CategoryCoupon): CategoryCoupon
    fun findById(id: CategoryCouponId): CategoryCoupon?
    fun findByCouponPolicyId(policyId: CouponPolicyId): List<CategoryCoupon>
    fun findByCategoryId(categoryId: CategoryId): List<CategoryCoupon>
    fun findByCouponPolicyIdAndCategoryId(
        policyId: CouponPolicyId,
        categoryId: CategoryId
    ): CategoryCoupon?
    fun existsByCouponPolicyIdAndCategoryId(
        policyId: CouponPolicyId,
        categoryId: CategoryId
    ): Boolean
    fun delete(categoryCoupon: CategoryCoupon)
    fun deleteById(id: CategoryCouponId)
    fun findAll(): List<CategoryCoupon>
}
