package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.application.port.out.CategoryCouponPort
import com.ps.app.coupons.domain.CategoryCoupon
import com.ps.app.coupons.domain.CategoryCouponId
import com.ps.app.coupons.domain.CouponPolicyId
import com.ps.app.products.domain.CategoryId
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

    override fun findById(id: CategoryCouponId): CategoryCoupon? {
        return categoryCouponRepository.findById(id.value)
            .map { CategoryCouponMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByCouponPolicyId(policyId: CouponPolicyId): List<CategoryCoupon> {
        return categoryCouponRepository.findByCouponPolicyId(policyId.value)
            .map { CategoryCouponMapper.toDomain(it) }
    }

    override fun findByCategoryId(categoryId: CategoryId): List<CategoryCoupon> {
        return categoryCouponRepository.findByCategoryId(categoryId.value)
            .map { CategoryCouponMapper.toDomain(it) }
    }

    override fun findByCouponPolicyIdAndCategoryId(
        policyId: CouponPolicyId,
        categoryId: CategoryId
    ): CategoryCoupon? {
        TODO("Not yet implemented")
    }

    fun findAll(): List<CategoryCoupon> {
        return categoryCouponRepository.findAll()
            .map { CategoryCouponMapper.toDomain(it) }
    }

    override fun save(categoryCoupon: CategoryCoupon): CategoryCoupon {
        val couponPolicy = couponPolicyRepository.findById(categoryCoupon.id.value)
            .orElseThrow {
                IllegalArgumentException("CouponPolicy not found: ${categoryCoupon.id.value}")
            }

        val entity = CategoryCouponMapper.toEntity(categoryCoupon, couponPolicy)
        val savedEntity = categoryCouponRepository.save(entity)
        return CategoryCouponMapper.toDomain(savedEntity)
    }

    override fun delete(categoryCoupon: CategoryCoupon) {
        categoryCouponRepository.deleteById(categoryCoupon.id.value)
    }

    override fun deleteById(id: CategoryCouponId) {
        categoryCouponRepository.deleteById(id.value)
    }

    override fun existsByCouponPolicyIdAndCategoryId(policyId: CouponPolicyId, categoryId: CategoryId): Boolean {
        return categoryCouponRepository.existsByCouponPolicyIdAndCategoryId(
            policyId.value,
            categoryId.value
        )
    }
}
