package com.ps.app.coupons.application.service

import com.ps.app.coupons.adapter.`in`.web.dto.CreateCouponTypeRequest
import com.ps.app.coupons.application.port.`in`.*
import com.ps.app.coupons.application.port.out.CouponTypePort
import com.ps.app.coupons.application.usecases.CreateCouponTypeUseCase
import com.ps.app.coupons.application.usecases.GetCouponTypeUseCase
import com.ps.app.coupons.domain.CouponType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponTypeService(
    private val couponTypePort: CouponTypePort
) : GetCouponTypeUseCase,
    CreateCouponTypeUseCase {

    @Transactional(readOnly = true)
    override fun getCouponType(query: GetCouponTypeQuery): CouponType {
        return when {
            query.id != null -> {
                couponTypePort.findById(query.id)
                    ?: throw IllegalArgumentException("CouponType not found: ${query.id}")
            }
            query.scope != null -> {
                couponTypePort.findByName(query.scope)
                    ?: throw IllegalArgumentException("CouponType not found: ${query.scope}")
            }
            else -> throw IllegalArgumentException("Either id or scope must be provided")
        }
    }

    @Transactional(readOnly = true)
    override fun getAllCouponTypes(): List<CouponType> {
        return couponTypePort.findAll()
    }

    override fun createCouponType(command: CreateCouponTypeRequest): CouponType {
        // 중복 검증
        if (couponTypePort.existsByName(command.scope)) {
            throw IllegalArgumentException("CouponType already exists: ${command.scope}")
        }

        val couponType = CouponType.create(command.scope)
        return couponTypePort.save(couponType)
    }
}
