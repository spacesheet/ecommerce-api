package com.ps.app.coupons.domain

import com.ps.app.coupons.domain.constant.CouponScope

data class CouponType(
    val id: CouponTypeId = CouponTypeId.NEW,
    val name: CouponScope
) {
    fun isGlobal(): Boolean = name == CouponScope.GLOBAL
    fun isSpecific(): Boolean = name == CouponScope.SPECIFIC
    fun isBook(): Boolean = name == CouponScope.BOOK
    fun isCategory(): Boolean = name == CouponScope.CATEGORY

    fun requiresCategoryRestriction(): Boolean = name.requiresCategoryRestriction()
    fun requiresProductRestriction(): Boolean = name.requiresProductRestriction()

    companion object {
        fun createGlobal(): CouponType = CouponType(name = CouponScope.GLOBAL)
        fun createSpecific(): CouponType = CouponType(name = CouponScope.SPECIFIC)
        fun createBook(): CouponType = CouponType(name = CouponScope.BOOK)
        fun createCategory(): CouponType = CouponType(name = CouponScope.CATEGORY)
        fun create(scope: CouponScope): CouponType = CouponType(name = scope)
    }
}
