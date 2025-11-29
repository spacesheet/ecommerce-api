package com.ps.app.infrastructure.persistence.entity

import com.ps.app.orders.adapter.out.persistence.OrdersEntity
import com.ps.app.orders.adapter.out.persistence.WrappingEntity
import com.ps.app.orders.domain.OrderDetailId
import com.ps.app.orders.domain.OrderStatus
import com.ps.app.products.adapter.out.persistence.ProductEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "order_detail",
    indexes = [
        Index(name = "idx_order_detail_order_id", columnList = "order_id"),
        Index(name = "idx_order_detail_product_id", columnList = "product_id"),
        Index(name = "idx_order_detail_status", columnList = "status"),
        Index(name = "idx_order_detail_create_at", columnList = "create_at")
    ]
)
class OrderDetailEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: OrderDetailId,

    @Column(nullable = false)
    var price: Int,

    @Column(nullable = false)
    var quantity: Int,

    @Column(nullable = false)
    var wrap: Boolean = false,

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wrapping_id")
    var wrapping: WrappingEntity? = null,

    // ⭐ Product 연관관계 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: ProductEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    val order: OrdersEntity,

    @Column(name = "create_at", nullable = false, updatable = false)
    val createAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "update_at", nullable = false)
    var updateAt: LocalDateTime = LocalDateTime.now()
) {
    @PreUpdate
    fun preUpdate() {
        updateAt = LocalDateTime.now()
    }

    fun updatePrice(newPrice: Int) {
        this.price = newPrice
    }

    fun updateQuantity(newQuantity: Int) {
        this.quantity = newQuantity
    }

    fun updateStatus(newStatus: OrderStatus) {
        this.orderStatus = newStatus
    }

    fun updateWrapping(newWrap: Boolean, newWrapping: WrappingEntity?) {
        this.wrap = newWrap
        this.wrapping = newWrapping
    }

    fun getTotalPrice(): Int {
        val wrappingPrice = if (wrap && wrapping != null) wrapping!!.price else 0
        return (price * quantity) + wrappingPrice
    }
}
