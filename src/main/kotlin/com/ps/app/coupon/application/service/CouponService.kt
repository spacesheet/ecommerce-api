package com.ps.app.coupon.application.service

import com.ps.app.coupon.application.port.`in`.*
import com.ps.app.coupon.application.port.out.CouponPort
import com.ps.app.coupon.application.port.out.CouponPolicyPort
import com.ps.app.coupon.application.usecases.GetAvailableCouponsUseCase
import com.ps.app.coupon.application.usecases.GetCouponByCodeUseCase
import com.ps.app.coupon.application.usecases.GetUserCouponsUseCase
import com.ps.app.coupon.application.usecases.IssueCouponUseCase
import com.ps.app.coupon.application.usecases.UseCouponUseCase
import com.ps.app.user.application.port.out.LoadUserAuthPort
import com.ps.app.coupon.domain.Coupon
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponService(
    private val couponPort: CouponPort,
    private val loadUserAuthPort: LoadUserAuthPort,
    private val couponPolicyPort: CouponPolicyPort,
) : UseCouponUseCase,
    GetAvailableCouponsUseCase,
    IssueCouponUseCase,
    GetCouponByCodeUseCase,
    GetUserCouponsUseCase {

    override fun getAvailableCoupons(query: GetAvailableCouponsQuery): List<Coupon> {
        return couponPort.findByOwnerId(query.ownerId)
            .filter { it.isAvailable() }
    }

    override fun useCoupon(command: UseCouponCommand): Coupon {
        // 1. 도메인 로직 실행
        val coupon = couponPort.findById(command.couponId)
            ?: throw IllegalArgumentException("Coupon not found")

        // 2. 비즈니스 규칙 검증 (도메인에 위임)
        val usedCoupon = coupon.use()

        // 3. 영속성 저장 (Output Port 사용)
        return couponPort.save(usedCoupon)
    }

    override fun issueCoupon(command: IssueCouponCommand): Coupon {
        // 1. 사용자 존재 확인
        loadUserAuthPort.findById(command.ownerId)
            ?: throw IllegalArgumentException("User not found")

        // 2. 정책 존재 확인
        couponPolicyPort.findById(command.couponPolicyId)
            ?: throw IllegalArgumentException("Policy not found")

        // 3. 중복 확인
        if (couponPort.existsByCouponCode(command.couponCode)) {
            throw IllegalArgumentException("Coupon code already exists")
        }

        // 4. 도메인 객체 생성
        val coupon = Coupon.create(
            ownerId = command.ownerId,
            couponPolicyId = command.couponPolicyId,
            couponCode = command.couponCode,
            validDays = command.validDays
        )

        // 5. 저장
        return couponPort.save(coupon)
    }

    /**
     * 쿠폰 코드로 조회
     */
    @Transactional(readOnly = true)
    override fun getCouponByCode(query: GetCouponByCodeQuery): Coupon {
        return couponPort.findByCouponCode(query.couponCode)
            ?: throw IllegalArgumentException("Coupon not found: ${query.couponCode}")
    }

    /**
     * 사용자의 쿠폰 목록 조회
     */
    @Transactional(readOnly = true)
    override fun getUserCoupons(query: GetUserCouponsQuery): List<Coupon> {
        val coupons = couponPort.findByOwnerId(query.userId)

        return coupons
            .filter { coupon ->
                // 상태 필터
                val statusMatch = query.status == null || coupon.status == query.status

                // 만료 필터
                val expiredMatch = query.includeExpired || !coupon.isExpired()

                statusMatch && expiredMatch
            }
    }
}
