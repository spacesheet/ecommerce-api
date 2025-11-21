package com.ps.app.coupon.application.port.`in`

import java.time.LocalDate

data class UpdateEndDateCommand(
    val policyId: Int,
    val newEndDate: LocalDate
)
