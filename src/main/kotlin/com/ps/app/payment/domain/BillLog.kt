package com.ps.app.payment.domain

import java.time.LocalDateTime

data class BillLog(
    val id: Long = 0,
    val payment: String,
    val price: Int,
    val payAt: LocalDateTime,
    val orderId: Long,
    val status: BillStatus,
    val paymentKey: String?,
    val cancelReason: String?
) {
    fun cancel(reason: String): BillLog {
        require(status.canCancel()) {
            "Cannot cancel payment in status: $status"
        }
        return copy(
            status = BillStatus.CANCELED,
            cancelReason = reason
        )
    }

    fun partialCancel(reason: String): BillLog {
        require(status.canCancel()) {
            "Cannot partially cancel payment in status: $status"
        }
        return copy(
            status = BillStatus.PARTIAL_CANCELED,
            cancelReason = reason
        )
    }

    fun refund(reason: String): BillLog {
        require(status.canRefund()) {
            "Cannot refund payment in status: $status"
        }
        return copy(
            status = BillStatus.REFUND,
            cancelReason = reason
        )
    }

    fun complete(): BillLog {
        require(status == BillStatus.BEFORE) {
            "Cannot complete payment in status: $status"
        }
        return copy(status = BillStatus.COMPLETED)
    }

    fun done(): BillLog {
        require(status == BillStatus.COMPLETED) {
            "Cannot mark as done in status: $status"
        }
        return copy(status = BillStatus.DONE)
    }

    fun isRefundable(): Boolean = status.canRefund()
    fun isCancelable(): Boolean = status.canCancel()

    companion object {
        fun create(
            payment: String,
            price: Int,
            orderId: Long,
            paymentKey: String? = null
        ): BillLog {
            return BillLog(
                payment = payment,
                price = price,
                payAt = LocalDateTime.now(),
                orderId = orderId,
                status = BillStatus.BEFORE,
                paymentKey = paymentKey,
                cancelReason = null
            )
        }
    }
}
