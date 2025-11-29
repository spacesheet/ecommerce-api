package com.ps.app.coupons.application.service

import com.ps.app.coupons.adapter.out.persistence.CouponPolicyMapper
import com.ps.app.coupons.application.port.`in`.*
import com.ps.app.coupons.application.port.out.CategoryCouponPort
import com.ps.app.coupons.application.port.out.CouponPolicyPort
import com.ps.app.coupons.application.usecases.CreateCategoryCouponUseCase
import com.ps.app.coupons.application.usecases.DeleteCategoryCouponUseCase
import com.ps.app.coupons.application.usecases.GetCategoryCouponsUseCase
import com.ps.app.coupons.domain.CategoryCoupon
import com.ps.app.coupons.domain.CategoryCouponId
import com.ps.app.coupons.domain.CouponPolicyId
import com.ps.app.products.domain.CategoryId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryCouponService(
    private val categoryCouponPort: CategoryCouponPort,
    private val couponPolicyPort: CouponPolicyPort
) : CreateCategoryCouponUseCase,
    GetCategoryCouponsUseCase,
    DeleteCategoryCouponUseCase {

    override fun createCategoryCoupon(command: CreateCategoryCouponCommand): CategoryCoupon {
        // 쿠폰 정책 존재 확인
        val couponPolicyEntity = couponPolicyPort.findById(command.couponPolicyId)
            ?: throw IllegalArgumentException("Coupon policy not found: ${command.couponPolicyId}")

        // 중복 확인
        if (categoryCouponPort.existsByCouponPolicyIdAndCategoryId(
                CouponPolicyId(command.couponPolicyId),
                CategoryId(command.categoryId)
            )
        ) {
            throw IllegalArgumentException(
                "Category coupon already exists for policy ${command.couponPolicyId} and category ${command.categoryId}"
            )
        }

        // 도메인 객체 생성
        val categoryCoupon = CategoryCoupon.create(
            couponPolicy = couponPolicyEntity,
            categoryId = CategoryId(command.categoryId)
        )

        return categoryCouponPort.save(categoryCoupon)
    }

    @Transactional(readOnly = true)
    override fun getCategoryCoupons(query: GetCategoryCouponsQuery): List<CategoryCoupon> {
        return when {
            query.couponPolicyId != null -> {
                categoryCouponPort.findByCouponPolicyId(CouponPolicyId(query.couponPolicyId))
            }
            query.categoryId != null -> {
                categoryCouponPort.findByCategoryId(CategoryId(query.categoryId))
            }
            else -> {
                categoryCouponPort.findAll()
            }
        }
    }

    override fun deleteCategoryCoupon(command: DeleteCategoryCouponCommand) {
        val categoryCoupon = categoryCouponPort.findById(CategoryCouponId(command.id))
            ?: throw IllegalArgumentException("Category coupon not found: ${command.id}")

        categoryCouponPort.delete(categoryCoupon)
    }
}
