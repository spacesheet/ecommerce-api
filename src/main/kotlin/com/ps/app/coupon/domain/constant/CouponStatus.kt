// Domain Layer (domain/constant/CouponStatus.kt)
package com.ps.app.coupon.domain.constant

enum class CouponStatus {
    AVAILABLE,  // 사용 가능
    USED,       // 사용 완료
    EXPIRED,    // 만료됨
    CANCELLED   // 취소됨
}
