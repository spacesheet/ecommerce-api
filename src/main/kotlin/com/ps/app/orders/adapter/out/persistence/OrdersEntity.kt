package com.ps.app.orders.adapter.out.persistence

import com.ps.app.infrastructure.persistence.entity.OrderDetailEntity
import com.ps.app.orders.domain.OrderStatus
import com.ps.app.user.adapter.out.persistence.UserEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class OrdersEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @field:Size(min = 1, max = 50)
    @field:NotNull
    var orderStr: String,

    @field:NotNull
    var price: Int,

    @field:Size(min = 1, max = 200)
    var request: String? = null,

    @field:Size(min = 1, max = 200)
    @field:NotNull
    var address: String,

    @field:Size(min = 1, max = 200)
    @field:NotNull
    var addressDetail: String,

    @field:NotNull
    var zipcode: Int,

    @field:NotNull
    var desiredDeliveryDate: LocalDate,

    @field:Size(min = 1, max = 20)
    @field:NotNull
    var receiver: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null,

    @field:Size(min = 1, max = 20)
    @field:NotNull
    var sender: String,

    @field:NotNull
    var senderContactNumber: String,

    @field:NotNull
    var receiverContactNumber: String,

    @field:Size(min = 1, max = 255)
    var orderEmail: String? = null,

    @field:Size(min = 1, max = 20)
    var couponCode: String? = null,

    var deliveryRate: Int = 0,

    var deductedPoints: Int? = null,

    var earnedPoints: Int? = null,

    var deductedCouponPrice: Int? = null,

    var orderStatus: OrderStatus,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var details: MutableList<OrderDetailEntity> = mutableListOf(),

    @Column(name = "create_at", nullable = false, updatable = false)
    val createAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "update_at", nullable = false)
    var updateAt: LocalDateTime = LocalDateTime.now()
)
