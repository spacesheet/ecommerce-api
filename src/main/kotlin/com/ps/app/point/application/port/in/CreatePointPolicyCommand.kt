package com.ps.app.point.application.port.`in`

data class CreatePointPolicyCommand(
    val name: String,
    val point: Int = 0,
    val rate: Double = 1.0
)
