package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.application.port.out.CategoryCouponPort
import com.ps.app.coupons.domain.CategoryCoupon
import org.springframework.stereotype.Component

/**
 * 카테고리 쿠폰 영속성 어댑터
 * Secondary Adapter
 */
@Component
class CategoryCouponPersistenceAdapter(
    private val categoryCouponRepository: CategoryCouponJpaRepository,
    private val couponPolicyRepository: CouponPolicyJpaRepository
) : CategoryCouponPort {

    override fun findById(id: Int): CategoryCoupon? {
        return categoryCouponRepository.findById(id)
            .map { CategoryCouponMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByCouponPolicyId(couponPolicyId: Int): List<CategoryCoupon> {
        return categoryCouponRepository.findByCouponPolicyId(couponPolicyId)
            .map { CategoryCouponMapper.toDomain(it) }
    }

    override fun findByCategoryId(categoryId: Int): List<CategoryCoupon> {
        return categoryCouponRepository.findByCategoryId(categoryId)
            .map { CategoryCouponMapper.toDomain(it) }
    }

    override fun findAll(): List<CategoryCoupon> {
        return categoryCouponRepository.findAll()
            .map { CategoryCouponMapper.toDomain(it) }
    }

    override fun save(categoryCoupon: CategoryCoupon): CategoryCoupon {
        val couponPolicy = couponPolicyRepository.findById(categoryCoupon.couponPolicyId)
            .orElseThrow {
                IllegalArgumentException("CouponPolicy not found: ${categoryCoupon.couponPolicyId}")
            }

        val entity = CategoryCouponMapper.toEntity(categoryCoupon, couponPolicy)
        val savedEntity = categoryCouponRepository.save(entity)
        return CategoryCouponMapper.toDomain(savedEntity)
    }

    override fun delete(id: Int) {
        categoryCouponRepository.deleteById(id)
    }

    override fun existsByCouponPolicyIdAndCategoryId(
        couponPolicyId: Int,
        categoryId: Int
    ): Boolean {
        return categoryCouponRepository.existsByCouponPolicyIdAndCategoryId(
            couponPolicyId,
            categoryId
        )
    }
}
