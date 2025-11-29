package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.domain.ProductCoupon
import com.ps.app.coupons.domain.ProductCouponId
import com.ps.app.products.domain.ProductId

object ProductCouponMapper {

    fun toDomain(entity: ProductCouponEntity): ProductCoupon {
        val couponPolicy = CouponPolicyMapper.toDomain(entity.couponPolicy)

        return ProductCoupon(
            id = ProductCouponId(entity.id),
            couponPolicy = couponPolicy,
            productId = ProductId(entity.productId)
        )
    }

    fun toEntity(
        domain: ProductCoupon,
        couponPolicyEntity: CouponPolicyEntity
    ): ProductCouponEntity {
        return ProductCouponEntity(
            id = domain.id.value,
            couponPolicy = couponPolicyEntity,
            productId = domain.productId.value
        )
    }

    fun toDomainList(entities: List<ProductCouponEntity>): List<ProductCoupon> {
        return entities.map { toDomain(it) }
    }

    fun toDomainSafe(entity: ProductCouponEntity?): ProductCoupon? {
        return entity?.let {
            runCatching {
                toDomain(it)
            }.onFailure { error ->
                println("Failed to map ProductCoupon ${entity.id}: ${error.message}")
            }.getOrNull()
        }
    }
}
