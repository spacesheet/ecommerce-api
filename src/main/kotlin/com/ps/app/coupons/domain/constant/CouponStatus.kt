// Domain Layer (domain/constant/CouponStatus.kt)
package com.ps.app.coupons.domain.constant

enum class CouponStatus(val displayName: String) {
    AVAILABLE("사용 가능"),
    USED("사용 완료"),
    EXPIRED("만료됨"),
    CANCELLED("취소됨");

    fun isAvailable(): Boolean = this == AVAILABLE
    fun isUsed(): Boolean = this == USED
    fun isExpired(): Boolean = this == EXPIRED
    fun isCancelled(): Boolean = this == CANCELLED
}
