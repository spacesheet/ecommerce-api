package com.ps.app.orders.adapter.out.persistence


import com.ps.app.products.adapter.out.persistence.ProductEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "order_detail")
class OrderDetailEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @field:NotNull
    var price: Int,

    @field:NotNull
    var quantity: Int,

    @field:NotNull
    @Column(columnDefinition = "TINYINT(1)")
    var wrap: Boolean,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_id", nullable = false)
    var orderStatus: OrderStatusEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wrapping_id")
    var wrapping: WrappingEntity? = null,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    var product: ProductEntity,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    var order: OrdersEntity,

    @field:NotNull
    var createAt: LocalDateTime = LocalDateTime.now(),

    @field:NotNull
    var updateAt: LocalDateTime = LocalDateTime.now()
)
