package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.application.port.out.CouponPolicyPort
import com.ps.app.coupons.domain.CouponPolicy
import com.ps.app.coupons.domain.CouponPolicyId
import com.ps.app.coupons.domain.CouponTypeId
import com.ps.app.coupons.domain.constant.DiscountType
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CouponPolicyPersistenceAdapter(
    private val couponPolicyRepository: CouponPolicyJpaRepository,
    private val couponTypeRepository: CouponTypeJpaRepository
) : CouponPolicyPort {

    override fun save(couponPolicy: CouponPolicy): CouponPolicy {
        val couponTypeEntity = couponTypeRepository.findById(couponPolicy.couponType.id.value)
            .orElseThrow { IllegalArgumentException("CouponType not found: ${couponPolicy.couponType.id}") }

        val entity = if (couponPolicy.isNew()) {
            CouponPolicyMapper.toEntity(couponPolicy, couponTypeEntity)
        } else {
            couponPolicyRepository.findById(couponPolicy.id.value)
                .orElseThrow { IllegalArgumentException("CouponPolicy not found: ${couponPolicy.id}") }
                .apply {
                    changeEndDate(couponPolicy.endDate)
                    if (couponPolicy.deleted) delete()
                }
        }

        val saved = couponPolicyRepository.save(entity)
        return CouponPolicyMapper.toDomain(saved)
    }

    override fun findById(id: CouponPolicyId): CouponPolicy? {
        return couponPolicyRepository.findById(id.value)
            .map { CouponPolicyMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findById(id: Int): CouponPolicy? {
        return couponPolicyRepository.findById(id)
            .map { CouponPolicyMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(): List<CouponPolicy> {
        return couponPolicyRepository.findAll()
            .map { CouponPolicyMapper.toDomain(it) }
    }

    override fun findActivePolicies(): List<CouponPolicy> {
        return couponPolicyRepository.findActivePolicies(LocalDate.now())
            .map { CouponPolicyMapper.toDomain(it) }
    }

    override fun findByName(name: String): CouponPolicy? {
        return couponPolicyRepository.findByNameAndDeletedFalse(name)
            ?.let { CouponPolicyMapper.toDomain(it) }
    }

    override fun findByCouponTypeId(couponTypeId: CouponTypeId): List<CouponPolicy> {
        return couponPolicyRepository.findByCouponTypeId(couponTypeId.value)
            .map { CouponPolicyMapper.toDomain(it) }
    }

    override fun findByDiscountType(discountType: DiscountType): List<CouponPolicy> {
        return couponPolicyRepository.findByDiscountType(discountType)
            .map { CouponPolicyMapper.toDomain(it) }
    }

    override fun findApplicablePolicies(orderAmount: Int): List<CouponPolicy> {
        return couponPolicyRepository.findApplicablePolicies(orderAmount, LocalDate.now())
            .map { CouponPolicyMapper.toDomain(it) }
    }

    override fun findExpiringPolicies(days: Int): List<CouponPolicy> {
        val now = LocalDate.now()
        val expiryDate = now.plusDays(days.toLong())
        return couponPolicyRepository.findExpiringPolicies(now, expiryDate)
            .map { CouponPolicyMapper.toDomain(it) }
    }

    override fun findExpiredPolicies(): List<CouponPolicy> {
        return couponPolicyRepository.findExpiredPolicies(LocalDate.now())
            .map { CouponPolicyMapper.toDomain(it) }
    }

    override fun searchByName(keyword: String): List<CouponPolicy> {
        return couponPolicyRepository.searchByName(keyword)
            .map { CouponPolicyMapper.toDomain(it) }
    }

    override fun existsByName(name: String): Boolean {
        return couponPolicyRepository.existsByNameAndDeletedFalse(name)
    }

    override fun delete(couponPolicy: CouponPolicy) {
        val deleted = couponPolicy.delete()
        save(deleted)
    }

    override fun delete(id: Int) {
        val policy = findById(CouponPolicyId(id))
            ?: throw IllegalArgumentException("CouponPolicy not found: $id")
        delete(policy)
    }
}
