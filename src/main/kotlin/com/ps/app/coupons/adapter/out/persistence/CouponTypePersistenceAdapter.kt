package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.application.port.out.CouponTypePort
import com.ps.app.coupons.domain.CouponType
import com.ps.app.coupons.domain.constant.CouponScope
import org.springframework.stereotype.Component

/**
 * 쿠폰 타입 영속성 어댑터
 * Secondary Adapter - Output Port 구현
 */
@Component
class CouponTypePersistenceAdapter(
    private val couponTypeRepository: CouponTypeJpaRepository
) : CouponTypePort {

    override fun findById(id: Int): CouponType? {
        return couponTypeRepository.findById(id)
            .map { CouponTypeMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByName(name: CouponScope): CouponType? {
        return couponTypeRepository.findByName(name.name)
            ?.let { CouponTypeMapper.toDomain(it) }
    }

    override fun findAll(): List<CouponType> {
        return couponTypeRepository.findAll()
            .map { CouponTypeMapper.toDomain(it) }
    }

    override fun save(couponType: CouponType): CouponType {
        val entity = CouponTypeMapper.toEntity(couponType)
        val savedEntity = couponTypeRepository.save(entity)
        return CouponTypeMapper.toDomain(savedEntity)
    }

    override fun delete(id: Int) {
        couponTypeRepository.deleteById(id)
    }

    override fun existsByName(name: CouponScope): Boolean {
        return couponTypeRepository.existsByName(name.name)
    }
}
