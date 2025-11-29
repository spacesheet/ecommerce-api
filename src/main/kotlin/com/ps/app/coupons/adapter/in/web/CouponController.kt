package com.ps.app.coupons.adapter.`in`.web

import com.ps.app.coupons.domain.constant.CouponStatus

import com.ps.app.coupons.adapter.`in`.web.dto.*
import com.ps.app.coupons.application.port.`in`.*
import com.ps.app.coupons.application.service.CouponService
import com.ps.app.coupons.application.usecases.GetAvailableCouponsUseCase
import com.ps.app.coupons.application.usecases.GetCouponByCodeUseCase
import com.ps.app.coupons.application.usecases.GetUserCouponsUseCase
import com.ps.app.coupons.application.usecases.IssueCouponUseCase
import com.ps.app.coupons.application.usecases.UseCouponUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/coupons")
class CouponController(
    private val couponService: CouponService,
    private val useCouponUseCase: UseCouponUseCase,
    private val getAvailableCouponsUseCase: GetAvailableCouponsUseCase,
    private val issueCouponUseCase: IssueCouponUseCase,
    private val getCouponByCodeUseCase: GetCouponByCodeUseCase,
    private val getUserCouponsUseCase: GetUserCouponsUseCase
) {

    /**
     * 쿠폰 사용
     */
    @PostMapping("/{id}/use")
    fun useCoupon(
        @PathVariable id: Long,
        @RequestBody request: UseCouponRequest
    ): CouponResponse {
        val command = UseCouponCommand(
            couponId = id,
            userId = request.userId
        )
        val coupon = useCouponUseCase.useCoupon(command)
        return CouponResponse.from(coupon)
    }

    /**
     * 사용 가능한 쿠폰 조회
     */
    @GetMapping("/available")
    fun getAvailableCoupons(
        @RequestParam ownerId: Long
    ): List<CouponResponse> {
        val query = GetAvailableCouponsQuery(ownerId)
        return getAvailableCouponsUseCase.getAvailableCoupons(query)
            .map { CouponResponse.from(it) }
    }

    /**
     * 쿠폰 발급
     */
    @PostMapping("/issue")
    @ResponseStatus(HttpStatus.CREATED)
    fun issueCoupon(
        @RequestBody request: IssueCouponRequest
    ): CouponResponse {
        val command = IssueCouponCommand(
            ownerId = request.ownerId,
            couponPolicyId = request.couponPolicyId,
            couponCode = request.couponCode,
            validDays = request.validDays
        )
        val coupon = issueCouponUseCase.issueCoupon(command)
        return CouponResponse.from(coupon)
    }

    /**
     * 쿠폰 코드로 조회
     */
    @GetMapping("/code/{couponCode}")
    fun getCouponByCode(
        @PathVariable couponCode: String
    ): CouponResponse {
        val query = GetCouponByCodeQuery(couponCode)
        val coupon = getCouponByCodeUseCase.getCouponByCode(query)
        return CouponResponse.from(coupon)
    }

    /**
     * 사용자의 쿠폰 목록 조회
     */
    @GetMapping("/user/{userId}")
    fun getUserCoupons(
        @PathVariable userId: Long,
        @RequestParam(required = false) status: String?,
        @RequestParam(defaultValue = "false") includeExpired: Boolean
    ): List<CouponResponse> {
        val query = GetUserCouponsQuery(
            userId = userId,
            status = status?.let { CouponStatus.valueOf(it) },
            includeExpired = includeExpired
        )
        return getUserCouponsUseCase.getUserCoupons(query)
            .map { CouponResponse.from(it) }
    }

    @GetMapping("/applicable")
    fun getApplicable(
        @RequestParam userId: Long,
        @RequestParam productId: Long,
        @RequestParam categoryId: Int,
        @RequestParam orderAmount: Int
    ): ResponseEntity<List<CouponResponse>> {
        val coupons = couponService.getApplicableCouponsForProduct(
            userId, orderAmount, productId, categoryId
        )
        return ResponseEntity.ok(coupons.map { CouponResponse.from(it) })
    }
}
