package com.ps.app.coupon.adapter.out.persistence

import com.ps.app.coupon.application.port.out.CouponPort
import com.ps.app.coupon.domain.Coupon
import org.springframework.stereotype.Component

@Component
class CouponPersistenceAdapter(
    private val couponRepository: CouponJpaRepository,
    private val couponMapper: CouponMapper
) : CouponPort {

    override fun findById(id: Long): Coupon? {
        return couponRepository.findById(id)
            .map { couponMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByOwnerId(ownerId: Long): List<Coupon> {
        return couponRepository.findByOwnerId(ownerId)
            .map { couponMapper.toDomain(it) }
    }

    override fun findByCouponCode(couponCode: String): Coupon? {
        return couponRepository.findByCouponCode(couponCode)
            ?.let { couponMapper.toDomain(it) }
    }

    override fun save(coupon: Coupon): Coupon {
        val entity = couponMapper.toEntity(coupon)
        val savedEntity = couponRepository.save(entity)
        return couponMapper.toDomain(savedEntity)
    }

    override fun existsByCouponCode(couponCode: String): Boolean {
        return couponRepository.existsByCouponCode(couponCode)
    }
}
