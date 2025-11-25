package com.ps.app.coupons.domain.constant

/**
 * 쿠폰 정책과 관련된 상수를 포함하는 클래스입니다.
 *
 * 웰컴 쿠폰, 생일 쿠폰과 같은 다양한 쿠폰 정책의 상수를 포함하고 있습니다.
 * 이 클래스는 싱글톤 객체입니다.
 *
 * **사용 예:**
 * ```kotlin
 * val welcomePolicy = CouponPolicyConstant.WELCOME_COUPON_POLICY_NAME
 * val birthdayPolicy = CouponPolicyConstant.BIRTHDAY_COUPON_POLICY_NAME
 * ```
 */
object CouponPolicyConstant {
    /**
     * 웰컴 쿠폰 정책의 이름을 나타내는 상수입니다.
     */
    const val WELCOME_COUPON_POLICY_NAME = "웰컴 쿠폰"

    /**
     * 생일 쿠폰 정책의 이름을 나타내는 상수입니다.
     */
    const val BIRTHDAY_COUPON_POLICY_NAME = "생일 쿠폰"
}
