package com.ps.app.orders.adapter.out.persistence


import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "delivery_policy")
class DeliveryPolicyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @field:Size(min = 1, max = 20)
    var name: String? = null,

    @field:NotNull
    var standardPrice: Int,

    @field:NotNull
    var policyPrice: Int,

    @field:NotNull
    var deleted: Boolean = false
)
