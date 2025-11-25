package com.ps.app.coupons.domain

import com.ps.app.coupons.domain.constant.CouponScope

/**
 * 쿠폰 타입 도메인 모델
 * 순수한 비즈니스 로직만 포함
 */
data class CouponType(
    val id: Int? = null,
    val name: CouponScope
) {
    init {
        // 도메인 규칙 검증
        require(name != null) { "Coupon scope cannot be null" }
    }

    /**
     * 전체 쿠폰인지 확인
     */
    fun isGlobal(): Boolean = name == CouponScope.GLOBAL

    /**
     * 특정 쿠폰인지 확인
     */
    fun isSpecific(): Boolean = name == CouponScope.SPECIFIC

    companion object {
        /**
         * 쿠폰 타입 생성
         */
        fun create(scope: CouponScope): CouponType {
            return CouponType(
                name = scope
            )
        }

        /**
         * 전체 쿠폰 타입 생성
         */
        fun createGlobal(): CouponType {
            return CouponType(name = CouponScope.GLOBAL)
        }

        /**
         * 특정 쿠폰 타입 생성
         */
        fun createSpecific(): CouponType {
            return CouponType(name = CouponScope.SPECIFIC)
        }
    }
}
