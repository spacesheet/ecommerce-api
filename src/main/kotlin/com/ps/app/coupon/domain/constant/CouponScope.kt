package com.ps.app.coupon.domain.constant

enum class CouponScope {
    GLOBAL,      // 전체 상품에 적용 가능
    SPECIFIC,    // 특정 상품/카테고리에만 적용
    BOOK,        // 도서에만 적용
    CATEGORY     // 특정 카테고리에만 적용
}
