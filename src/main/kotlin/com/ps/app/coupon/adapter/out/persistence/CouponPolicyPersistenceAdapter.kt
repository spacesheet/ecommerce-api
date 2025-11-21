package com.ps.app.coupon.adapter.out.persistence

import com.ps.app.coupon.application.port.out.CouponPolicyPort
import com.ps.app.coupon.domain.CouponPolicy
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CouponPolicyPersistenceAdapter(
    private val couponPolicyRepository: CouponPolicyJpaRepository,
    private val couponTypeRepository: CouponTypeJpaRepository
) : CouponPolicyPort {

    override fun findById(id: Long): CouponPolicy? {
        return couponPolicyRepository.findById(id)
            .map { CouponPolicyMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(): List<CouponPolicy> {
        return couponPolicyRepository.findAll()
            .map { CouponPolicyMapper.toDomain(it) }
    }

    override fun findActivePolicies(): List<CouponPolicy> {
        val now = LocalDate.now()
        return couponPolicyRepository.findByDeletedFalseAndStartDateLessThanEqualAndEndDateGreaterThanEqual(now, now)
            .map { CouponPolicyMapper.toDomain(it) }
    }

    override fun findByName(name: String): CouponPolicy? {
        return couponPolicyRepository.findByName(name)
            ?.let { CouponPolicyMapper.toDomain(it) }
    }

    override fun save(couponPolicy: CouponPolicy): CouponPolicy {
        val couponType = couponTypeRepository.findById(couponPolicy.couponTypeId)
            .orElseThrow {
                IllegalArgumentException("CouponType not found: ${couponPolicy.couponTypeId}")
            }

        val entity = CouponPolicyMapper.toEntity(couponPolicy, couponType)
        val savedEntity = couponPolicyRepository.save(entity)
        return CouponPolicyMapper.toDomain(savedEntity)
    }

    override fun delete(id: Int) {
        couponPolicyRepository.deleteById(id)
    }

    override fun existsByName(name: String): Boolean {
        return couponPolicyRepository.existsByName(name)
    }
}
