package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.application.port.out.CouponsPort
import com.ps.app.coupons.domain.Coupons
import org.springframework.stereotype.Component

@Component
class CouponsPersistenceAdapter(
    private val couponRepository: CouponJpaRepository,
    private val couponsMapper: CouponsMapper
) : CouponsPort {

    override fun findById(id: Long): Coupons? {
        return couponRepository.findById(id)
            .map { couponsMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByOwnerId(ownerId: Long): List<Coupons> {
        return couponRepository.findByOwnerId(ownerId)
            .map { couponsMapper.toDomain(it) }
    }

    override fun findByCouponCode(couponCode: String): Coupons? {
        return couponRepository.findByCouponCode(couponCode)
            ?.let { couponsMapper.toDomain(it) }
    }

    override fun save(coupons: Coupons): Coupons {
        val entity = couponsMapper.toEntity(coupons)
        val savedEntity = couponRepository.save(entity)
        return couponsMapper.toDomain(savedEntity)
    }

    override fun existsByCouponCode(couponCode: String): Boolean {
        return couponRepository.existsByCouponCode(couponCode)
    }
}
