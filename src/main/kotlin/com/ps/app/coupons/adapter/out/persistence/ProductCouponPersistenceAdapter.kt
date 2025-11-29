package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.application.port.out.ProductCouponPort
import com.ps.app.coupons.domain.ProductCoupon
import com.ps.app.coupons.domain.ProductCouponId
import com.ps.app.coupons.domain.CouponPolicyId
import com.ps.app.products.domain.ProductId
import org.springframework.stereotype.Component

@Component
class ProductCouponPersistenceAdapter(
    private val productCouponRepository: ProductCouponJpaRepository,
    private val couponPolicyRepository: CouponPolicyJpaRepository
) : ProductCouponPort {

    override fun save(productCoupon: ProductCoupon): ProductCoupon {
        val policyEntity = couponPolicyRepository.findById(productCoupon.couponPolicy.id.value)
            .orElseThrow { IllegalArgumentException("CouponPolicy not found: ${productCoupon.couponPolicy.id}") }

        val entity = if (productCoupon.isNew()) {
            ProductCouponMapper.toEntity(productCoupon, policyEntity)
        } else {
            productCouponRepository.findById(productCoupon.id.value)
                .orElseThrow { IllegalArgumentException("ProductCoupon not found: ${productCoupon.id}") }
        }

        val saved = productCouponRepository.save(entity)
        return ProductCouponMapper.toDomain(saved)
    }

    override fun findById(id: ProductCouponId): ProductCoupon? {
        return productCouponRepository.findById(id.value)
            .map { ProductCouponMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByCouponPolicyId(policyId: CouponPolicyId): List<ProductCoupon> {
        return productCouponRepository.findByCouponPolicyId(policyId.value)
            .map { ProductCouponMapper.toDomain(it) }
    }

    override fun findByProductId(productId: ProductId): List<ProductCoupon> {
        return productCouponRepository.findByProductId(productId.value)
            .map { ProductCouponMapper.toDomain(it) }
    }

    override fun findByCouponPolicyIdAndProductId(
        policyId: CouponPolicyId,
        productId: ProductId
    ): ProductCoupon? {
        return productCouponRepository.findByCouponPolicyIdAndProductId(
            policyId.value,
            productId.value
        )?.let { ProductCouponMapper.toDomain(it) }
    }

    override fun existsByCouponPolicyIdAndProductId(
        policyId: CouponPolicyId,
        productId: ProductId
    ): Boolean {
        return productCouponRepository.existsByCouponPolicyIdAndProductId(
            policyId.value,
            productId.value
        )
    }

    override fun delete(productCoupon: ProductCoupon) {
        productCouponRepository.deleteById(productCoupon.id.value)
    }

    override fun deleteById(id: ProductCouponId) {
        productCouponRepository.deleteById(id.value)
    }
}
