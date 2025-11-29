package com.ps.app.coupons.application.port.out

import com.ps.app.coupons.domain.ProductCoupon
import com.ps.app.coupons.domain.ProductCouponId
import com.ps.app.coupons.domain.CouponPolicyId
import com.ps.app.products.domain.ProductId

interface ProductCouponPort {
    fun save(productCoupon: ProductCoupon): ProductCoupon
    fun findById(id: ProductCouponId): ProductCoupon?
    fun findByCouponPolicyId(policyId: CouponPolicyId): List<ProductCoupon>
    fun findByProductId(productId: ProductId): List<ProductCoupon>
    fun findByCouponPolicyIdAndProductId(
        policyId: CouponPolicyId,
        productId: ProductId
    ): ProductCoupon?
    fun existsByCouponPolicyIdAndProductId(
        policyId: CouponPolicyId,
        productId: ProductId
    ): Boolean
    fun delete(productCoupon: ProductCoupon)
    fun deleteById(id: ProductCouponId)
}
