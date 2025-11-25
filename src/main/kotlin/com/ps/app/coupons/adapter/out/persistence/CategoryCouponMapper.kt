package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.domain.CategoryCoupon

/**
 * CategoryCoupon Domain ↔ Entity 변환 Mapper
 */
object CategoryCouponMapper {

    /**
     * Entity를 Domain 모델로 변환
     */
    fun toDomain(entity: CategoryCouponEntity): CategoryCoupon {
        return CategoryCoupon(
            id = entity.id,
            couponPolicyId = entity.couponPolicy.id
                ?: throw IllegalStateException("CouponPolicy id cannot be null"),
            categoryId = entity.categoryId
        )
    }

    /**
     * Entity를 Domain 모델로 안전하게 변환 (null 허용)
     */
    fun toDomainOrNull(entity: CategoryCouponEntity?): CategoryCoupon? {
        return entity?.let { toDomain(it) }
    }

    /**
     * Domain 모델을 Entity로 변환
     */
    fun toEntity(
        domain: CategoryCoupon,
        couponPolicy: CouponPolicyEntity
    ): CategoryCouponEntity {
        return CategoryCouponEntity(
            id = domain.id,
            couponPolicy = couponPolicy,
            categoryId = domain.categoryId
        )
    }

    /**
     * Entity 리스트를 Domain 리스트로 변환
     */
    fun toDomainList(entities: List<CategoryCouponEntity>): List<CategoryCoupon> {
        return entities.map { toDomain(it) }
    }

    /**
     * Domain 리스트를 Entity 리스트로 변환 (단일 CouponPolicy)
     */
    fun toEntityList(
        domains: List<CategoryCoupon>,
        couponPolicy: CouponPolicyEntity
    ): List<CategoryCouponEntity> {
        return domains.map { domain ->
            require(domain.couponPolicyId == couponPolicy.id) {
                "Domain couponPolicyId ${domain.couponPolicyId} does not match provided policy ${couponPolicy.id}"
            }
            toEntity(domain, couponPolicy)
        }
    }

    /**
     * Domain 리스트를 Entity 리스트로 변환 (CouponPolicy 맵 사용)
     */
    fun toEntityList(
        domains: List<CategoryCoupon>,
        couponPolicyMap: Map<Int, CouponPolicyEntity>
    ): List<CategoryCouponEntity> {
        return domains.map { domain ->
            val couponPolicy = couponPolicyMap[domain.couponPolicyId]
                ?: throw IllegalArgumentException(
                    "CouponPolicy not found in map: ${domain.couponPolicyId}"
                )
            toEntity(domain, couponPolicy)
        }
    }

    /**
     * 기존 Entity를 Domain 정보로 업데이트
     * (불변 Entity의 경우 새 인스턴스 반환)
     */
    fun updateEntity(
        entity: CategoryCouponEntity,
        domain: CategoryCoupon,
        couponPolicy: CouponPolicyEntity
    ): CategoryCouponEntity {
        return CategoryCouponEntity(
            id = entity.id,
            couponPolicy = couponPolicy,
            categoryId = domain.categoryId
        )
    }
}
