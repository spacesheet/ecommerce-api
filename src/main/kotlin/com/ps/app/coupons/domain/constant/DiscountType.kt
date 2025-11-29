package com.ps.app.coupons.domain.constant

/**
 * 할인 유형을 나타내는 enum 클래스입니다.
 *
 * RATE 와 AMOUNT 의 두 가지 할인 유형이 있습니다.
 * 이 클래스는 Jackson 라이브러리와 통합되어 JSON 직렬화 및 역직렬화를 지원합니다.
 */
enum class DiscountType(val displayName: String) {
    PERCENTAGE("정률 할인"),
    FIXED_AMOUNT("정액 할인");
}
