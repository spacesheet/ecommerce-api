package com.ps.app.payment.adapter.out.persistence

import com.ps.app.orders.adapter.out.persistence.OrdersEntity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

@Entity
@Table(name = "bill_log")
class BillLogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @field:Size(min = 1, max = 20)
    @field:NotNull
    var payment: String,

    @field:NotNull
    var price: Int,

    @field:NotNull
    var payAt: LocalDateTime,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    var order: OrdersEntity,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    var status: BillStatusEntity,

    var paymentKey: String? = null,

    @field:Size(max = 255)
    var cancelReason: String? = null
)
