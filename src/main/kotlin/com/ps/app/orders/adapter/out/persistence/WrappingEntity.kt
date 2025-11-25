package com.ps.app.orders.adapter.out.persistence


import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "wrapping")
class WrappingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @field:Size(min = 1, max = 50)
    @field:NotNull
    var paper: String,

    @field:NotNull
    var price: Int,

    @field:NotNull
    var deleted: Boolean = false
)
