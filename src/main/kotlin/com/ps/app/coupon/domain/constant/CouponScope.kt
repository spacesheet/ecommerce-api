package com.ps.app.coupon.domain.constant

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

/**
 * 쿠폰의 적용 범위를 나타내는 enum 클래스입니다.
 *
 * GLOBAL, BOOK, CATEGORY 의 세 가지 범위가 있습니다.
 * 이 클래스는 Jackson 라이브러리와 통합되어 JSON 직렬화 및 역직렬화를 지원합니다.
 */
enum class CouponScope {
    GLOBAL, BOOK, CATEGORY;

    /**
     * enum 값을 소문자 문자열로 반환합니다.
     *
     * @return 소문자로 변환된 enum 값
     */
    @JsonValue
    override fun toString(): String {
        return name.lowercase()
    }

    companion object {
        /**
         * 문자열을 입력받아 해당하는 CouponScope enum 값을 반환합니다.
         *
         * 입력된 문자열이 null 이거나 유효한 enum 값이 아닐 경우 IllegalArgumentException 을 발생시킵니다.
         *
         * @param value 입력된 문자열
         * @return 입력된 문자열에 해당하는 CouponScope 값
         * @throws IllegalArgumentException 유효하지 않은 값이 입력된 경우
         */
        @JsonCreator
        @JvmStatic
        fun fromString(value: String?): CouponScope {
            if (value == null) {
                throw IllegalArgumentException("enum 값을 찾을 수 없습니다.")
            }
            return when (value.lowercase()) {
                "global" -> GLOBAL
                "book" -> BOOK
                "category" -> CATEGORY
                else -> throw IllegalArgumentException("enum 값을 찾을 수 없습니다.")
            }
        }
    }
}
