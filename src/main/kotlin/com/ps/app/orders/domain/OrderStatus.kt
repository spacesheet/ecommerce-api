package com.ps.app.orders.domain

enum class OrderStatus(
    val displayName: String,
    val description: String
) {
    PENDING("주문대기", "주문이 접수되어 확인 대기 중입니다"),
    CONFIRMED("주문확정", "주문이 확정되어 처리 중입니다"),
    PROCESSING("상품준비중", "상품 준비가 진행 중입니다"),
    SHIPPING("배송중", "상품이 배송 중입니다"),
    SHIPPED("배송완료", "배송이 완료되었습니다"),
    DELIVERED("수령완료", "고객이 상품을 수령했습니다"),
    CANCELLED("주문취소", "주문이 취소되었습니다"),
    REFUNDED("환불완료", "환불이 완료되었습니다"),
    RETURNED("반품완료", "상품이 반품되었습니다");

    // 다음 상태로 전환 가능 여부 확인
    fun canTransitionTo(target: OrderStatus): Boolean {
        return when (this) {
            PENDING -> target in setOf(CONFIRMED, CANCELLED)
            CONFIRMED -> target in setOf(PROCESSING, CANCELLED)
            PROCESSING -> target in setOf(SHIPPING, CANCELLED)
            SHIPPING -> target in setOf(SHIPPED, CANCELLED)
            SHIPPED -> target in setOf(DELIVERED, RETURNED)
            DELIVERED -> target in setOf(RETURNED)
            CANCELLED -> target == REFUNDED
            REFUNDED -> false
            RETURNED -> target == REFUNDED
        }
    }

    // 가능한 다음 상태들 반환
    fun getNextStatuses(): Set<OrderStatus> {
        return when (this) {
            PENDING -> setOf(CONFIRMED, CANCELLED)
            CONFIRMED -> setOf(PROCESSING, CANCELLED)
            PROCESSING -> setOf(SHIPPING, CANCELLED)
            SHIPPING -> setOf(SHIPPED, CANCELLED)
            SHIPPED -> setOf(DELIVERED, RETURNED)
            DELIVERED -> setOf(RETURNED)
            CANCELLED -> setOf(REFUNDED)
            REFUNDED -> emptySet()
            RETURNED -> setOf(REFUNDED)
        }
    }

    // 배송 완료 여부
    fun isDelivered(): Boolean = this in setOf(SHIPPED, DELIVERED)

    // 수정 가능 여부
    fun isModifiable(): Boolean = this in setOf(PENDING)

    // 취소 가능 여부
    fun isCancellable(): Boolean = this in setOf(PENDING, CONFIRMED, PROCESSING, SHIPPING)

    // 반품 가능 여부
    fun isReturnable(): Boolean = this in setOf(SHIPPED, DELIVERED)

    // 최종 상태 여부
    fun isFinalStatus(): Boolean = this in setOf(REFUNDED)

    // 진행 중인 상태 여부
    fun isInProgress(): Boolean = this in setOf(CONFIRMED, PROCESSING, SHIPPING, SHIPPED)

    companion object {
        fun fromName(name: String): OrderStatus {
            return entries.find { it.name == name.uppercase() }
                ?: throw IllegalArgumentException("Unknown order status: $name")
        }

        // 모든 활성 상태 (취소/환불/반품 제외)
        fun getActiveStatuses(): Set<OrderStatus> {
            return setOf(PENDING, CONFIRMED, PROCESSING, SHIPPING, SHIPPED, DELIVERED)
        }

        // 완료된 상태들
        fun getCompletedStatuses(): Set<OrderStatus> {
            return setOf(DELIVERED, CANCELLED, REFUNDED, RETURNED)
        }
    }
}
