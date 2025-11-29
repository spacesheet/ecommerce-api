package com.ps.app.coupons.domain

import com.ps.app.products.domain.ProductId

/**
 * 특정 상품 쿠폰 도메인 모델
 * CouponScope.SPECIFIC 타입 쿠폰에 사용
 */
data class ProductCoupon(
    val id: ProductCouponId = ProductCouponId.NEW,
    val couponPolicy: CouponPolicy,
    val productId: ProductId
) {
    init {
        require(couponPolicy.couponType.isSpecific()) {
            "ProductCoupon can only be created with SPECIFIC coupon type"
        }
    }

    companion object {
        fun create(
            couponPolicy: CouponPolicy,
            productId: ProductId
        ): ProductCoupon {
            return ProductCoupon(
                id = ProductCouponId.NEW,
                couponPolicy = couponPolicy,
                productId = productId
            )
        }
    }

    fun isNew(): Boolean = id.isNew()

    fun isApplicableToProduct(targetProductId: ProductId): Boolean {
        return productId == targetProductId
    }

    fun isApplicableToProduct(targetProductId: Long): Boolean {
        return productId.value == targetProductId
    }

    fun isActive(): Boolean = couponPolicy.isActive()
}

