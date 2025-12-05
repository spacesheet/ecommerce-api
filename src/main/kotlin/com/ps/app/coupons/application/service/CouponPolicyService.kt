package com.ps.app.coupons.application.service

import com.ps.app.coupons.application.port.out.CouponPolicyPort
import com.ps.app.coupons.application.port.out.CouponTypePort
import com.ps.app.coupons.domain.*
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
    fun createAmountCouponPolicy(
        couponTypeId: Int,
        name: String,
        discountAmount: Int,
        conditions: List<CouponCondition>
    ): AmountCouponPolicy {
        require(conditions.isNotEmpty()) { "At least one condition is required" }

        // 정책명 중복 확인
        require(!couponPolicyPort.existsByName(name)) {
            "Coupon policy name already exists: $name"
        }

        val couponType = couponTypePort.findById(CouponTypeId(couponTypeId))
            ?: throw IllegalArgumentException("CouponType not found: $couponTypeId")

        val couponPolicy = AmountCouponPolicy.create(
            couponType = couponType,
            name = name,
            discountAmount = discountAmount,
            conditions = conditions.toTypedArray()
        )

        return couponPolicyPort.save(couponPolicy) as AmountCouponPolicy
    }

    @Transactional
    fun createPercentCouponPolicy(
        couponTypeId: Int,
        name: String,
        discountRate: Double,
        conditions: List<CouponCondition>
    ): PercentCouponPolicy {
        require(conditions.isNotEmpty()) { "At least one condition is required" }

        // 정책명 중복 확인
        require(!couponPolicyPort.existsByName(name)) {
            "Coupon policy name already exists: $name"
        }

        val couponType = couponTypePort.findById(CouponTypeId(couponTypeId))
            ?: throw IllegalArgumentException("CouponType not found: $couponTypeId")

        val couponPolicy = PercentCouponPolicy.create(
            couponType = couponType,
            name = name,
            discountRate = discountRate,
            conditions = conditions.toTypedArray()
        )

        return couponPolicyPort.save(couponPolicy) as PercentCouponPolicy
    }

    @Transactional
    fun createCouponPolicy(
        couponTypeId: Int,
        name: String,
        discountType: DiscountType,
        discountRate: Double = 0.0,
        discountAmount: Int = 0,
        conditions: List<CouponCondition>
    ): CouponPolicy {
        return when (discountType) {
            DiscountType.FIXED_AMOUNT -> createAmountCouponPolicy(
                couponTypeId = couponTypeId,
                name = name,
                discountAmount = discountAmount,
                conditions = conditions
            )
            DiscountType.PERCENTAGE -> createPercentCouponPolicy(
                couponTypeId = couponTypeId,
                name = name,
                discountRate = discountRate,
                conditions = conditions
            )
        }
    }

    // 단일 조건으로 쿠폰 정책 생성 (편의 메서드)
    @Transactional
    fun createCouponPolicyWithSingleCondition(
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
        val condition = CouponCondition(
            period = period,
            standardPrice = standardPrice,
            maxDiscountAmount = maxDiscountAmount,
            startDate = startDate,
            endDate = endDate
        )

        return createCouponPolicy(
            couponTypeId = couponTypeId,
            name = name,
            discountType = discountType,
            discountRate = discountRate,
            discountAmount = discountAmount,
            conditions = listOf(condition)
        )
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

    fun getPolicyById(policyId: Int): CouponPolicy {
        return couponPolicyPort.findById(CouponPolicyId(policyId))
            ?: throw IllegalArgumentException("CouponPolicy not found: $policyId")
    }

    fun getAmountPolicies(): List<AmountCouponPolicy> {
        return couponPolicyPort.findActivePolicies()
            .filterIsInstance<AmountCouponPolicy>()
    }

    fun getPercentPolicies(): List<PercentCouponPolicy> {
        return couponPolicyPort.findActivePolicies()
            .filterIsInstance<PercentCouponPolicy>()
    }

    @Transactional
    fun extendPolicyEndDate(policyId: Int, newEndDate: LocalDate): CouponPolicy {
        val policy = couponPolicyPort.findById(CouponPolicyId(policyId))
            ?: throw IllegalArgumentException("CouponPolicy not found: $policyId")

        val updated = policy.changeEndDate(newEndDate)
        return couponPolicyPort.save(updated)
    }

    @Transactional
    fun addConditionToPolicy(policyId: Int, condition: CouponCondition): CouponPolicy {
        val policy = couponPolicyPort.findById(CouponPolicyId(policyId))
            ?: throw IllegalArgumentException("CouponPolicy not found: $policyId")

        val newConditions = policy.conditions + condition
        val updated = when (policy) {
            is AmountCouponPolicy -> policy.copy(policyConditions = newConditions)
            is PercentCouponPolicy -> policy.copy(policyConditions = newConditions)
            else -> throw IllegalStateException("Unknown policy type")
        }

        return couponPolicyPort.save(updated)
    }

    @Transactional
    fun deleteCouponPolicy(policyId: Int) {
        val policy = couponPolicyPort.findById(CouponPolicyId(policyId))
            ?: throw IllegalArgumentException("CouponPolicy not found: $policyId")

        val deleted = policy.delete()
        couponPolicyPort.save(deleted)
    }

    @Transactional
    fun hardDeleteCouponPolicy(policyId: Int) {
        val policy = couponPolicyPort.findById(CouponPolicyId(policyId))
            ?: throw IllegalArgumentException("CouponPolicy not found: $policyId")

        couponPolicyPort.delete(policy)
    }
}
