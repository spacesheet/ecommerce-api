package com.ps.app.point.domain

import java.time.LocalDateTime

data class PointLog(
    val id: Long = 0,
    val userId: Long,
    val createAt: LocalDateTime,
    val inquiry: String,
    val delta: Int,
    val balance: Int
) {
    init {
        require(inquiry.isNotBlank()) { "Inquiry cannot be blank" }
    }

    companion object {
        fun create(
            userId: Long,
            inquiry: String,
            delta: Int,
            balance: Int
        ): PointLog {
            return PointLog(
                userId = userId,
                createAt = LocalDateTime.now(),
                inquiry = inquiry,
                delta = delta,
                balance = balance
            )
        }
    }
}
