package com.ps.app.coupons.application.service

import com.ps.app.coupons.application.port.out.CouponPolicyPort
import com.ps.app.coupons.application.port.out.CouponTypePort
import com.ps.app.coupons.domain.CouponPolicy
import com.ps.app.coupons.domain.CouponPolicyId
import com.ps.app.coupons.domain.CouponTypeId
import com.ps.app.coupons.domain.constant.DiscountType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class CouponPolicyService(
    private val couponPolicyPort: CouponPolicyPort,
    private val couponTypePort: CouponTypePort
) {

    @Transactional
    fun createCouponPolicy(
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
        // 정책명 중복 확인
        require(!couponPolicyPort.existsByName(name)) {
            "Coupon policy name already exists: $name"
        }

        val couponType = couponTypePort.findById(CouponTypeId(couponTypeId))
            ?: throw IllegalArgumentException("CouponType not found: $couponTypeId")

        val couponPolicy = CouponPolicy.create(
            couponType = couponType,
            name = name,
            discountType = discountType,
            discountRate = discountRate,
            discountAmount = discountAmount,
            period = period,
            standardPrice = standardPrice,
            maxDiscountAmount = maxDiscountAmount,
            startDate = startDate,
            endDate = endDate
        )

        return couponPolicyPort.save(couponPolicy)
    }

    fun getActivePolicies(): List<CouponPolicy> {
        return couponPolicyPort.findActivePolicies()
    }

    fun getApplicablePolicies(orderAmount: Int): List<CouponPolicy> {
        return couponPolicyPort.findApplicablePolicies(orderAmount)
            .filter { it.canApplyTo(orderAmount) }
    }

    fun calculateBestDiscount(orderAmount: Int): CouponPolicy? {
        return getApplicablePolicies(orderAmount)
            .maxByOrNull { it.calculateDiscount(orderAmount) }
    }

    @Transactional
    fun extendPolicyEndDate(policyId: Int, newEndDate: LocalDate): CouponPolicy {
        val policy = couponPolicyPort.findById(CouponPolicyId(policyId))
            ?: throw IllegalArgumentException("CouponPolicy not found: $policyId")

        val updated = policy.changeEndDate(newEndDate)
        return couponPolicyPort.save(updated)
    }

    @Transactional
    fun deleteCouponPolicy(policyId: Int) {
        val policy = couponPolicyPort.findById(CouponPolicyId(policyId))
            ?: throw IllegalArgumentException("CouponPolicy not found: $policyId")

        couponPolicyPort.delete(policy)
    }
}
