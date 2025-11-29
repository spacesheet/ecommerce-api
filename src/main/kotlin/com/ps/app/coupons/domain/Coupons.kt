package com.ps.app.coupons.domain

import com.ps.app.coupons.domain.constant.CouponStatus
import com.ps.app.user.domain.UserId
import com.ps.app.products.domain.CategoryId
import com.ps.app.products.domain.ProductId
import java.time.LocalDate

/**
 * 사용자에게 발급된 쿠폰
 * CouponPolicy 기반으로 생성됨
 */
data class Coupons(
    val id: CouponsId = CouponsId.NEW,
    val ownerId: UserId,
    val couponPolicy: CouponPolicy,
    val couponCode: String,
    val createDate: LocalDate,
    val expireDate: LocalDate,
    val couponStatus: CouponStatus,
) {
    init {
        require(couponCode.isNotBlank()) { "Coupon code cannot be blank" }
        require(expireDate >= createDate) { "Expire date must be after or equal to create date" }
    }

    companion object {
        /**
         * 쿠폰 정책 기반으로 쿠폰 생성
         */
        fun createFromPolicy(
            ownerId: UserId,
            couponPolicy: CouponPolicy,
            couponCode: String
        ): Coupons {
            require(couponPolicy.isActive()) { "Cannot issue coupon with inactive policy" }

            val now = LocalDate.now()
            return Coupons(
                id = CouponsId.NEW,
                ownerId = ownerId,
                couponPolicy = couponPolicy,
                couponCode = couponCode.uppercase().trim(),
                createDate = now,
                expireDate = now.plusDays(couponPolicy.period.toLong()),
                couponStatus = CouponStatus.AVAILABLE
            )
        }

        /**
         * 커스텀 만료일로 쿠폰 생성
         */
        fun create(
            ownerId: UserId,
            couponPolicy: CouponPolicy,
            couponCode: String,
            expireDate: LocalDate
        ): Coupons {
            require(couponPolicy.isActive()) { "Cannot issue coupon with inactive policy" }

            val now = LocalDate.now()
            require(expireDate >= now) { "Expire date must be in the future" }

            return Coupons(
                id = CouponsId.NEW,
                ownerId = ownerId,
                couponPolicy = couponPolicy,
                couponCode = couponCode.uppercase().trim(),
                createDate = now,
                expireDate = expireDate,
                couponStatus = CouponStatus.AVAILABLE
            )
        }
    }

    // ========== 상태 확인 메서드 ==========

    fun isNew(): Boolean = id.isNew()

    fun isExpired(): Boolean = LocalDate.now().isAfter(expireDate)

    fun isAvailable(): Boolean = couponStatus == CouponStatus.AVAILABLE && !isExpired()

    fun isUsed(): Boolean = couponStatus == CouponStatus.USED

    fun isExpiredStatus(): Boolean = couponStatus == CouponStatus.EXPIRED

    // ========== 상태 변경 메서드 ==========

    /**
     * 쿠폰 사용
     */
    fun use(): Coupons {
        require(couponStatus == CouponStatus.AVAILABLE) { "Only available coupons can be used" }
        require(!isExpired()) { "Cannot use expired coupon" }
        require(couponPolicy.isActive()) { "Cannot use coupon with inactive policy" }
        return copy(couponStatus = CouponStatus.USED)
    }

    /**
     * 쿠폰 사용 취소
     */
    fun cancel(): Coupons {
        require(couponStatus == CouponStatus.USED) { "Only used coupons can be cancelled" }
        return copy(couponStatus = CouponStatus.AVAILABLE)
    }

    /**
     * 쿠폰 만료 처리
     */
    fun expire(): Coupons {
        require(couponStatus == CouponStatus.AVAILABLE) { "Only available coupons can be expired" }
        return copy(couponStatus = CouponStatus.EXPIRED)
    }

    // ========== 쿠폰 적용 가능 여부 확인 ==========

    /**
     * 특정 상품에 적용 가능한지 확인 (SPECIFIC)
     */
    fun canApplyToProduct(
        productId: ProductId,
        productCoupons: List<ProductCoupon>
    ): Boolean {
        if (!couponPolicy.couponType.isSpecific()) {
            return false
        }

        return productCoupons.any {
            it.couponPolicy.id == couponPolicy.id &&
                    it.isApplicableToProduct(productId)
        }
    }

    /**
     * 특정 카테고리에 적용 가능한지 확인 (CATEGORY)
     */
    fun canApplyToCategory(
        categoryId: CategoryId,
        categoryCoupons: List<CategoryCoupon>
    ): Boolean {
        if (!couponPolicy.couponType.isCategory()) {
            return false
        }

        return categoryCoupons.any {
            it.couponPolicy.id == couponPolicy.id &&
                    it.isApplicableToCategory(categoryId)
        }
    }

    /**
     * 도서 카테고리에 적용 가능한지 확인 (BOOK)
     */
    fun canApplyToBook(
        categoryId: CategoryId,
        categoryCoupons: List<CategoryCoupon>
    ): Boolean {
        if (!couponPolicy.couponType.isBook()) {
            return false
        }

        return categoryCoupons.any {
            it.couponPolicy.id == couponPolicy.id &&
                    it.isApplicableToCategory(categoryId) &&
                    it.isBookCoupon()
        }
    }

    /**
     * 주문 아이템에 적용 가능한지 확인 (통합)
     */
    fun canApplyToOrderItem(
        orderAmount: Int,
        productId: ProductId? = null,
        categoryId: CategoryId? = null,
        productCoupons: List<ProductCoupon> = emptyList(),
        categoryCoupons: List<CategoryCoupon> = emptyList()
    ): Boolean {
        // 1. 기본 조건 확인
        if (!isAvailable() || !couponPolicy.canApplyTo(orderAmount)) {
            return false
        }

        // 2. GLOBAL - 모든 상품에 적용 가능
        if (couponPolicy.couponType.isGlobal()) {
            return true
        }

        // 3. SPECIFIC - 특정 상품만
        if (couponPolicy.couponType.isSpecific()) {
            productId ?: return false
            return canApplyToProduct(productId, productCoupons)
        }

        // 4. CATEGORY - 특정 카테고리만
        if (couponPolicy.couponType.isCategory()) {
            categoryId ?: return false
            return canApplyToCategory(categoryId, categoryCoupons)
        }

        // 5. BOOK - 도서 카테고리만
        if (couponPolicy.couponType.isBook()) {
            categoryId ?: return false
            return canApplyToBook(categoryId, categoryCoupons)
        }

        return false
    }

    // ========== 비즈니스 로직 메서드 ==========

    /**
     * 할인 금액 계산
     */
    fun calculateDiscount(orderAmount: Int): Int {
        require(isAvailable()) { "Cannot calculate discount for unavailable coupon" }
        require(couponPolicy.canApplyTo(orderAmount)) { "Order amount does not meet minimum requirement" }

        return couponPolicy.calculateDiscount(orderAmount)
    }

    /**
     * 최종 가격 계산
     */
    fun calculateFinalPrice(orderAmount: Int): Int {
        return couponPolicy.calculateFinalPrice(orderAmount)
    }

    /**
     * 사용자 소유 확인
     */
    fun belongsToUser(userId: UserId): Boolean {
        return ownerId == userId
    }

    fun belongsToUser(userId: Long): Boolean {
        return ownerId.value == userId
    }

    // ========== 쿠폰 타입 확인 편의 메서드 ==========

    fun isGlobalCoupon(): Boolean = couponPolicy.couponType.isGlobal()

    fun isSpecificCoupon(): Boolean = couponPolicy.couponType.isSpecific()

    fun isBookCoupon(): Boolean = couponPolicy.couponType.isBook()

    fun isCategoryCoupon(): Boolean = couponPolicy.couponType.isCategory()

    /**
     * 할인 타입 확인
     */
    fun isPercentageDiscount(): Boolean = couponPolicy.isPercentageDiscount()

    fun isFixedAmountDiscount(): Boolean = couponPolicy.isFixedAmountDiscount()

    // ========== 쿠폰 정보 및 유틸리티 ==========

    /**
     * 쿠폰 정보 요약
     */
    fun getSummary(): CouponSummary {
        return CouponSummary(
            couponCode = couponCode,
            policyName = couponPolicy.name,
            couponScope = couponPolicy.couponType.name.displayName,
            discountType = couponPolicy.discountType.displayName,
            discountRate = couponPolicy.discountRate,
            discountAmount = couponPolicy.discountAmount,
            minOrderAmount = couponPolicy.standardPrice,
            maxDiscountAmount = couponPolicy.maxDiscountAmount,
            expireDate = expireDate,
            status = couponStatus.displayName,
            isAvailable = isAvailable()
        )
    }

    /**
     * 만료까지 남은 일수
     */
    fun getDaysUntilExpiry(): Long {
        val now = LocalDate.now()
        return if (expireDate.isAfter(now)) {
            java.time.temporal.ChronoUnit.DAYS.between(now, expireDate)
        } else {
            0L
        }
    }

    /**
     * 만료 임박 여부 (N일 이내)
     */
    fun isExpiringSoon(days: Int = 7): Boolean {
        return isAvailable() && getDaysUntilExpiry() <= days
    }
}


