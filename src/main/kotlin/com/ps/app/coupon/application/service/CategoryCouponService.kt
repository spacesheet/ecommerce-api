package com.ps.app.coupon.application.service

import com.ps.app.coupon.application.port.`in`.*
import com.ps.app.coupon.application.port.out.CategoryCouponPort
import com.ps.app.coupon.application.port.out.CouponPolicyPort
import com.ps.app.coupon.application.usecases.CreateCategoryCouponUseCase
import com.ps.app.coupon.application.usecases.DeleteCategoryCouponUseCase
import com.ps.app.coupon.application.usecases.GetCategoryCouponsUseCase
import com.ps.app.coupon.domain.CategoryCoupon
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
        couponPolicyPort.findById(command.couponPolicyId)
            ?: throw IllegalArgumentException("Coupon policy not found: ${command.couponPolicyId}")

        // 중복 확인
        if (categoryCouponPort.existsByCouponPolicyIdAndCategoryId(
                command.couponPolicyId,
                command.categoryId
            )
        ) {
            throw IllegalArgumentException(
                "Category coupon already exists for policy ${command.couponPolicyId} and category ${command.categoryId}"
            )
        }

        // 도메인 객체 생성
        val categoryCoupon = CategoryCoupon.create(
            couponPolicyId = command.couponPolicyId,
            categoryId = command.categoryId
        )

        return categoryCouponPort.save(categoryCoupon)
    }

    @Transactional(readOnly = true)
    override fun getCategoryCoupons(query: GetCategoryCouponsQuery): List<CategoryCoupon> {
        return when {
            query.couponPolicyId != null -> {
                categoryCouponPort.findByCouponPolicyId(query.couponPolicyId)
            }
            query.categoryId != null -> {
                categoryCouponPort.findByCategoryId(query.categoryId)
            }
            else -> {
                categoryCouponPort.findAll()
            }
        }
    }

    override fun deleteCategoryCoupon(command: DeleteCategoryCouponCommand) {
        categoryCouponPort.findById(command.id)
            ?: throw IllegalArgumentException("Category coupon not found: ${command.id}")

        categoryCouponPort.delete(command.id)
    }
}
