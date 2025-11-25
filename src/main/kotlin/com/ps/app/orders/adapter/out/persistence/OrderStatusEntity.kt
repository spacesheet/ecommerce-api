package com.ps.app.orders.adapter.out.persistence


import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "order_status")
class OrderStatusEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @field:NotNull
    var name: String,

    @field:NotNull
    var updateAt: LocalDateTime = LocalDateTime.now()
)
