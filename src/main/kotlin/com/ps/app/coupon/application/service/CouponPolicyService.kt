package com.ps.app.coupon.application.service

import com.ps.app.coupon.application.port.`in`.*
import com.ps.app.coupon.application.port.out.CouponPolicyPort
import com.ps.app.coupon.application.usecases.CreateCouponPolicyUseCase
import com.ps.app.coupon.application.usecases.DeleteCouponPolicyUseCase
import com.ps.app.coupon.application.usecases.GetActiveCouponPoliciesUseCase
import com.ps.app.coupon.application.usecases.UpdateCouponPolicyEndDateUseCase
import com.ps.app.coupon.domain.CouponPolicy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponPolicyService(
    private val couponPolicyPort: CouponPolicyPort
) : CreateCouponPolicyUseCase,
    UpdateCouponPolicyEndDateUseCase,
    DeleteCouponPolicyUseCase,
    GetActiveCouponPoliciesUseCase {

    override fun createCouponPolicy(command: CreateCouponPolicyCommand): CouponPolicy {
        // 중복 이름 검증
        if (couponPolicyPort.existsByName(command.name)) {
            throw IllegalArgumentException("Coupon policy name already exists: ${command.name}")
        }

        // 도메인 객체 생성
        val policy = CouponPolicy.create(
            couponTypeId = command.couponTypeId,
            name = command.name,
            discountType = command.discountType,
            discountRate = command.discountRate,
            discountAmount = command.discountAmount,
            period = command.period,
            standardPrice = command.standardPrice,
            maxDiscountAmount = command.maxDiscountAmount,
            startDate = command.startDate,
            endDate = command.endDate
        )

        return couponPolicyPort.save(policy)
    }

    override fun updateEndDate(command: UpdateEndDateCommand): CouponPolicy {
        val policy = couponPolicyPort.findById(command.policyId)
            ?: throw IllegalArgumentException("Policy not found: ${command.policyId}")

        val updatedPolicy = policy.changeEndDate(command.newEndDate)
        return couponPolicyPort.save(updatedPolicy)
    }

    @Transactional(readOnly = true)
    override fun getActivePolicies(): List<CouponPolicy> {
        return couponPolicyPort.findActivePolicies()
    }

    override fun deleteCouponPolicy(command: DeleteCouponPolicyCommand) {
        val policy = couponPolicyPort.findById(command.policyId)
            ?: throw IllegalArgumentException("Policy not found: ${command.policyId}")

        val deletedPolicy = policy.delete()
        couponPolicyPort.save(deletedPolicy)
    }
}
