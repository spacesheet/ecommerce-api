package com.ps.app.coupons.domain.constant

enum class CouponScope(val displayName: String, val description: String) {
    GLOBAL("전체 쿠폰", "모든 상품에 적용 가능"),
    SPECIFIC("특정 상품 쿠폰", "특정 상품에만 적용"),
    BOOK("도서 쿠폰", "도서 카테고리 상품에만 적용"),
    CATEGORY("카테고리 쿠폰", "특정 카테고리 상품에만 적용");

    fun isGlobal(): Boolean = this == GLOBAL
    fun isSpecific(): Boolean = this == SPECIFIC
    fun isBook(): Boolean = this == BOOK
    fun isCategory(): Boolean = this == CATEGORY

    fun requiresCategoryRestriction(): Boolean = this in setOf(BOOK, CATEGORY)
    fun requiresProductRestriction(): Boolean = this == SPECIFIC
}
