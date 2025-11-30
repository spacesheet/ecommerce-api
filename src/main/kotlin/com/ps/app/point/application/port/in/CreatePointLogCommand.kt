package com.ps.app.point.application.port.`in`

data class CreatePointLogCommand(
    val userId: Long,
    val inquiry: String,
    val delta: Int,
    val balance: Int
)
