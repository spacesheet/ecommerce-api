package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.domain.CouponsId
import com.ps.app.coupons.domain.constant.CouponStatus
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

@Entity
@Table(
    name = "coupons",
    indexes = [
        Index(name = "idx_coupons_owner_id", columnList = "owner_id"),
        Index(name = "idx_coupons_policy_id", columnList = "coupon_policy_id"),
        Index(name = "idx_coupons_code", columnList = "coupon_code", unique = true),
        Index(name = "idx_coupons_status", columnList = "status"),
        Index(name = "idx_coupons_expire_date", columnList = "expire_date")
    ]
)
@EntityListeners(AuditingEntityListener::class)
class CouponsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "owner_id", nullable = false)
    val ownerId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_policy_id", nullable = false)
    val couponPolicy: CouponPolicyEntity,

    @Column(name = "coupon_code", nullable = false, unique = true, length = 50)
    val couponCode: String,

    @CreatedDate
    @Column(name = "create_date", nullable = false, updatable = false)
    val createDate: LocalDate = LocalDate.now(),

    @Column(name = "expire_date", nullable = false)
    val expireDate: LocalDate,

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    var couponStatus: CouponStatus
) {
    fun use() {
        require(couponStatus == CouponStatus.AVAILABLE) { "Only available coupons can be used" }
        require(!isExpired()) { "Cannot use expired coupon" }
        this.couponStatus = CouponStatus.USED
    }

    fun cancel() {
        require(couponStatus == CouponStatus.USED) { "Only used coupons can be cancelled" }
        this.couponStatus = CouponStatus.AVAILABLE
    }

    fun expire() {
        require(couponStatus == CouponStatus.AVAILABLE) { "Only available coupons can be expired" }
        this.couponStatus = CouponStatus.EXPIRED
    }

    fun isExpired(): Boolean {
        return LocalDate.now().isAfter(expireDate)
    }

    fun isAvailable(): Boolean {
        return couponStatus == CouponStatus.AVAILABLE && !isExpired()
    }

    fun isCurrentlyActive(): Boolean {
        return isAvailable() && couponPolicy.isActive()
    }
}
