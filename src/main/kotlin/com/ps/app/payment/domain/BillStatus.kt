package com.ps.app.payment.domain


enum class BillStatus {
    BEFORE,           // 결제 전
    COMPLETED,        // 결제 완료
    CANCELED,         // 결제 취소
    REFUND,          // 환불
    DONE,            // 처리 완료
    PARTIAL_CANCELED; // 부분 취소

    fun canCancel(): Boolean {
        return this == COMPLETED || this == PARTIAL_CANCELED
    }

    fun canRefund(): Boolean {
        return this == COMPLETED || this == PARTIAL_CANCELED
    }

    fun isCompleted(): Boolean {
        return this == COMPLETED || this == DONE
    }

    fun isCanceled(): Boolean {
        return this == CANCELED || this == PARTIAL_CANCELED
    }
}
