package com.ps.app.coupons.domain

import com.ps.app.coupons.domain.constant.DiscountType
import java.time.LocalDate

/**
 * 쿠폰 정책 도메인 모델
 * 순수한 비즈니스 로직만 포함하며, JPA 의존성이 없음
 */
data class CouponPolicy(
    val id: Int? = null,
    val couponTypeId: Int,
    val name: String,
    val discountType: DiscountType,
    val discountRate: Double,
    val discountAmount: Int,
    val period: Int,
    val standardPrice: Int,
    val maxDiscountAmount: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val deleted: Boolean = false
) {
    init {
        require(name.isNotBlank()) { "Coupon policy name cannot be blank" }
        require(name.length <= 30) { "Coupon policy name must be 30 characters or less" }
        require(discountRate >= 0) { "Discount rate cannot be negative" }
        require(discountAmount >= 0) { "Discount amount cannot be negative" }
        require(period > 0) { "Period must be positive" }
        require(standardPrice >= 0) { "Standard price cannot be negative" }
        require(maxDiscountAmount >= 0) { "Max discount amount cannot be negative" }
        require(endDate >= startDate) { "End date must be after or equal to start date" }

        // 할인 타입에 따른 검증
        when (discountType) {
            DiscountType.PERCENTAGE -> {
                require(discountRate > 0) { "Discount rate must be positive for PERCENTAGE type" }
                require(discountRate <= 100) { "Discount rate cannot exceed 100%" }
            }
            DiscountType.FIXED_AMOUNT -> {
                require(discountAmount > 0) { "Discount amount must be positive for FIXED_AMOUNT type" }
            }
        }
    }

    /**
     * 쿠폰 정책의 종료일을 변경합니다.
     */
    fun changeEndDate(newEndDate: LocalDate): CouponPolicy {
        require(newEndDate >= startDate) { "End date must be after or equal to start date" }
        require(!deleted) { "Cannot modify deleted policy" }
        return this.copy(endDate = newEndDate)
    }

    /**
     * 쿠폰 정책을 삭제 상태로 변경합니다.
     */
    fun delete(): CouponPolicy {
        require(!deleted) { "Policy is already deleted" }
        return this.copy(deleted = true)
    }

    /**
     * 쿠폰 정책이 현재 활성 상태인지 확인합니다.
     */
    fun isActive(): Boolean {
        val now = LocalDate.now()
        return !deleted && now >= startDate && now <= endDate
    }

    /**
     * 쿠폰 정책이 만료되었는지 확인합니다.
     */
    fun isExpired(): Boolean {
        return LocalDate.now().isAfter(endDate)
    }

    /**
     * 주어진 가격에 대한 할인 금액을 계산합니다.
     */
    fun calculateDiscount(originalPrice: Int): Int {
        require(originalPrice >= 0) { "Original price cannot be negative" }
        require(!deleted) { "Cannot use deleted policy" }
        require(isActive()) { "Cannot use inactive policy" }
        require(originalPrice >= standardPrice) {
            "Original price must be at least $standardPrice to apply this coupon"
        }

        val calculatedDiscount = when (discountType) {
            DiscountType.PERCENTAGE -> {
                (originalPrice * discountRate / 100).toInt()
            }
            DiscountType.FIXED_AMOUNT -> {
                discountAmount
            }
        }

        // 최대 할인 금액 제한 적용
        return minOf(calculatedDiscount, maxDiscountAmount)
    }

    /**
     * 할인 후 최종 가격을 계산합니다.
     */
    fun calculateFinalPrice(originalPrice: Int): Int {
        val discount = calculateDiscount(originalPrice)
        return maxOf(0, originalPrice - discount)
    }

    companion object {
        /**
         * 새로운 쿠폰 정책을 생성합니다.
         */
        fun create(
            couponTypeId: Int,
            name: String,
            discountType: DiscountType,
            discountRate: Double = 0.0,
            discountAmount: Int = 0,
            period: Int,
            standardPrice: Int,
            maxDiscountAmount: Int,
            startDate: LocalDate,
            endDate: LocalDate
        ): CouponPolicy {
            return CouponPolicy(
                couponTypeId = couponTypeId,
                name = name,
                discountType = discountType,
                discountRate = discountRate,
                discountAmount = discountAmount,
                period = period,
                standardPrice = standardPrice,
                maxDiscountAmount = maxDiscountAmount,
                startDate = startDate,
                endDate = endDate,
                deleted = false
            )
        }
    }
}
