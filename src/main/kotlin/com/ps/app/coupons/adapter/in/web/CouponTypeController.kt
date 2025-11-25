package com.ps.app.coupons.adapter.`in`.web

import com.ps.app.coupons.adapter.`in`.web.dto.CouponTypeResponse
import com.ps.app.coupons.adapter.`in`.web.dto.CreateCouponTypeRequest
import com.ps.app.coupons.application.port.`in`.*
import com.ps.app.coupons.application.usecases.CreateCouponTypeUseCase
import com.ps.app.coupons.application.usecases.GetCouponTypeUseCase
import com.ps.app.coupons.domain.constant.CouponScope
import org.springframework.web.bind.annotation.*

/**
 * 쿠폰 타입 컨트롤러
 * Primary Adapter - Input Port 호출
 */
@RestController
@RequestMapping("/api/coupon-types")
class CouponTypeController(
    private val getCouponTypeUseCase: GetCouponTypeUseCase,
    private val createCouponTypeUseCase: CreateCouponTypeUseCase
) {

    /**
     * 모든 쿠폰 타입 조회
     */
    @GetMapping
    fun getAllCouponTypes(): List<CouponTypeResponse> {
        return getCouponTypeUseCase.getAllCouponTypes()
            .map { CouponTypeResponse.from(it) }
    }

    /**
     * ID로 쿠폰 타입 조회
     */
    @GetMapping("/{id}")
    fun getCouponTypeById(@PathVariable id: Int): CouponTypeResponse {
        val query = GetCouponTypeQuery(id = id)
        val couponType = getCouponTypeUseCase.getCouponType(query)
        return CouponTypeResponse.from(couponType)
    }

    /**
     * Scope로 쿠폰 타입 조회
     */
    @GetMapping("/scope/{scope}")
    fun getCouponTypeByScope(@PathVariable scope: CouponScope): CouponTypeResponse {
        val query = GetCouponTypeQuery(scope = scope)
        val couponType = getCouponTypeUseCase.getCouponType(query)
        return CouponTypeResponse.from(couponType)
    }

    /**
     * 쿠폰 타입 생성
     */
    @PostMapping
    fun createCouponType(
        @RequestBody request: CreateCouponTypeRequest
    ): CouponTypeResponse {
        val command = CreateCouponTypeRequest(scope = request.scope)
        val couponType = createCouponTypeUseCase.createCouponType(command)
        return CouponTypeResponse.from(couponType)
    }
}
