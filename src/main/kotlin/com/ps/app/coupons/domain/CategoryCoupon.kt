package com.ps.app.coupons.domain

import com.ps.app.products.domain.CategoryId

/**
 * 카테고리 쿠폰 도메인 모델
 * CouponScope.CATEGORY 또는 CouponScope.BOOK 타입 쿠폰에 사용
 */
data class CategoryCoupon(
    val id: CategoryCouponId = CategoryCouponId.NEW,
    val couponPolicy: CouponPolicy,
    val categoryId: CategoryId
) {
    init {
        // CATEGORY 또는 BOOK 타입만 카테고리 쿠폰 가능
        require(couponPolicy.couponType.requiresCategoryRestriction()) {
            "CategoryCoupon can only be created with CATEGORY or BOOK coupon type"
        }
    }

    companion object {
        fun create(
            couponPolicy: CouponPolicy,
            categoryId: CategoryId
        ): CategoryCoupon {
            return CategoryCoupon(
                id = CategoryCouponId.NEW,
                couponPolicy = couponPolicy,
                categoryId = categoryId
            )
        }

        /**
         * 도서 쿠폰 생성 (특정 카테고리 ID 사용)
         */
        fun createBookCoupon(
            couponPolicy: CouponPolicy,
            bookCategoryId: CategoryId
        ): CategoryCoupon {
            require(couponPolicy.couponType.isBook()) {
                "Policy must have BOOK coupon type"
            }

            return CategoryCoupon(
                id = CategoryCouponId.NEW,
                couponPolicy = couponPolicy,
                categoryId = bookCategoryId
            )
        }
    }

    fun isNew(): Boolean = id.isNew()

    fun isApplicableToCategory(targetCategoryId: CategoryId): Boolean {
        return categoryId == targetCategoryId
    }

    fun isApplicableToCategory(targetCategoryId: Int): Boolean {
        return categoryId.value == targetCategoryId
    }

    fun isActive(): Boolean = couponPolicy.isActive()

    fun isBookCoupon(): Boolean = couponPolicy.couponType.isBook()

    fun isCategoryCoupon(): Boolean = couponPolicy.couponType.isCategory()
}

