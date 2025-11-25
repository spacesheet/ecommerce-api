package com.ps.app.coupons.domain

import com.ps.app.coupons.domain.constant.CouponStatus
import java.time.LocalDate

data class Coupons(
    val id: Long? = null,
    val ownerId: Long,
    val couponPolicyId: Long,
    val couponCode: String,
    val createDate: LocalDate,
    val expireDate: LocalDate,
    val status: CouponStatus
) {
    fun use(): Coupons {
        require(status == CouponStatus.AVAILABLE) { "Only available coupons can be used" }
        require(!isExpired()) { "Cannot use expired coupon" }
        return this.copy(status = CouponStatus.USED)
    }

    fun isExpired(): Boolean = LocalDate.now().isAfter(expireDate)

    fun isAvailable(): Boolean = status == CouponStatus.AVAILABLE && !isExpired()

    companion object {
        fun create(
            ownerId: Long,
            couponPolicyId: Long,
            couponCode: String,
            validDays: Int
        ): Coupons {
            val now = LocalDate.now()
            return Coupons(
                ownerId = ownerId,
                couponPolicyId = couponPolicyId,
                couponCode = couponCode,
                createDate = now,
                expireDate = now.plusDays(validDays.toLong()),
                status = CouponStatus.AVAILABLE
            )
        }
    }
}
