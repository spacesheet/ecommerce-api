package com.ps.app.coupons.domain

/**
 * 카테고리 쿠폰 도메인 모델
 * 특정 카테고리에만 적용 가능한 쿠폰
 */
data class CategoryCoupon(
    val id: Int? = null,
    val couponPolicyId: Int,
    val categoryId: Int
) {
    init {
        require(categoryId > 0) { "Category ID must be positive" }
    }

    /**
     * 특정 카테고리에 적용 가능한지 확인
     */
    fun isApplicableToCategory(targetCategoryId: Int): Boolean {
        return this.categoryId == targetCategoryId
    }

    companion object {
        /**
         * 카테고리 쿠폰 생성
         */
        fun create(
            couponPolicyId: Int,
            categoryId: Int
        ): CategoryCoupon {
            require(couponPolicyId > 0) { "Coupon policy ID must be positive" }
            require(categoryId > 0) { "Category ID must be positive" }

            return CategoryCoupon(
                couponPolicyId = couponPolicyId,
                categoryId = categoryId
            )
        }
    }
}
