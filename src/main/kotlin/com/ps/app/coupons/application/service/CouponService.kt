package com.ps.app.coupons.application.service

import com.ps.app.coupons.application.port.`in`.*
import com.ps.app.coupons.application.port.out.CategoryCouponPort
import com.ps.app.coupons.application.port.out.CouponsPort
import com.ps.app.coupons.application.port.out.CouponPolicyPort
import com.ps.app.coupons.application.port.out.ProductCouponPort
import com.ps.app.coupons.application.usecases.*
import com.ps.app.coupons.domain.CouponPolicyId
import com.ps.app.user.application.port.out.LoadUserAuthPort
import com.ps.app.coupons.domain.Coupons
import com.ps.app.coupons.domain.constant.CouponScope
import com.ps.app.products.domain.CategoryId
import com.ps.app.products.domain.ProductId
import com.ps.app.user.domain.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponService(
    private val couponsPort: CouponsPort,
    private val loadUserAuthPort: LoadUserAuthPort,
    private val productCouponPort: ProductCouponPort,
    private val categoryCouponPort: CategoryCouponPort,
    private val couponPolicyPort: CouponPolicyPort,
) : UseCouponUseCase,
    GetAvailableCouponsUseCase,
    IssueCouponUseCase,
    GetCouponByCodeUseCase,
    GetUserCouponsUseCase {

    /**
     * 상품 구매 시 적용 가능한 쿠폰 조회
     * ✅ Long 파라미터 → Service에서 Value Object로 변환
     */
    @Transactional(readOnly = true)
    fun getApplicableCouponsForProduct(
        userId: Long,
        orderAmount: Int,
        productId: Long,
        categoryId: Int
    ): List<Coupons> {
        val userCoupons = couponsPort.findAvailableCouponsByOwnerId(UserId(userId))

        val productCoupons = productCouponPort.findByProductId(ProductId(productId))
        val categoryCoupons = categoryCouponPort.findByCategoryId(CategoryId(categoryId))

        return userCoupons.filter { coupon ->
            coupon.canApplyToOrderItem(
                orderAmount = orderAmount,
                productId = ProductId(productId),
                categoryId = CategoryId(categoryId),
                productCoupons = productCoupons,
                categoryCoupons = categoryCoupons
            )
        }
    }

    /**
     * 쿠폰 타입별 적용 가능 쿠폰 개수
     */
    @Transactional(readOnly = true)
    fun getCouponCountByScope(userId: Long): Map<CouponScope, Int> {
        val coupons = couponsPort.findAvailableCouponsByOwnerId(UserId(userId))

        return coupons.groupBy { it.couponPolicy.couponType.name }
            .mapValues { it.value.size }
    }

    /**
     * 최대 할인 쿠폰 찾기
     * ✅ Long 파라미터
     */
    @Transactional(readOnly = true)
    fun findBestCouponForProduct(
        userId: Long,
        orderAmount: Int,
        productId: Long,
        categoryId: Int
    ): Coupons? {
        return getApplicableCouponsForProduct(userId, orderAmount, productId, categoryId)
            .maxByOrNull { it.calculateDiscount(orderAmount) }
    }

    override fun useCoupon(command: UseCouponCommand): Coupons {
        val coupon = couponsPort.findById(command.couponId)
            ?: throw IllegalArgumentException("Coupon not found")

        val usedCoupon = coupon.use()

        return couponsPort.save(usedCoupon)
    }

    override fun issueCoupon(command: IssueCouponCommand): Coupons {
        loadUserAuthPort.findById(command.ownerId)
            ?: throw IllegalArgumentException("User not found")

        val policy = couponPolicyPort.findById(CouponPolicyId(command.couponPolicyId))
            ?: throw IllegalArgumentException("Policy not found")

        if (couponsPort.existsByCouponCode(command.couponCode)) {
            throw IllegalArgumentException("Coupon code already exists")
        }

        val coupons = Coupons.createFromPolicy(
            ownerId = UserId(command.ownerId),
            couponPolicy = policy,
            couponCode = command.couponCode
        )

        return couponsPort.save(coupons)
    }

    @Transactional(readOnly = true)
    override fun getCouponByCode(query: GetCouponByCodeQuery): Coupons {
        return couponsPort.findByCouponCode(query.couponCode)
            ?: throw IllegalArgumentException("Coupon not found: ${query.couponCode}")
    }

    @Transactional(readOnly = true)
    override fun getUserCoupons(query: GetUserCouponsQuery): List<Coupons> {
        val coupons = couponsPort.findByOwnerId(query.userId)

        return coupons.filter { coupon ->
            val statusMatch = query.status == null || coupon.couponStatus == query.status
            val expiredMatch = query.includeExpired || !coupon.isExpired()

            statusMatch && expiredMatch
        }
    }

    @Transactional(readOnly = true)
    override fun getAvailableCoupons(query: GetAvailableCouponsQuery): List<Coupons> {
        return couponsPort.findByOwnerId(query.ownerId)
            .filter { it.isAvailable() }
    }
}
