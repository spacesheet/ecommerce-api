package com.ps.app.orders.domain

import com.ps.app.orders.adapter.out.persistence.OrdersEntity
import com.ps.app.products.adapter.out.persistence.CategoryMapper
import com.ps.app.products.adapter.out.persistence.ProductEntity
import com.ps.app.products.adapter.out.persistence.ProductMapper
import com.ps.app.products.domain.Product
import com.ps.app.user.domain.UserId
import java.time.LocalDateTime

data class OrderDetail(
    val id: OrderDetailId = OrderDetailId.NEW,
    val price: Int,
    val quantity: Int,
    val wrap: Boolean,
    val orderStatus: OrderStatus,
    val wrapping: Wrapping?,
    val product: ProductEntity,
    val order: OrdersEntity,
    val createAt: LocalDateTime,
    val updateAt: LocalDateTime,
) {
    companion object {
        fun create(
            price: Int,
            quantity: Int,
            wrap: Boolean,
            wrapping: Wrapping?,
            product: Product,
            order: Orders
        ): OrderDetail {
            require(price > 0) { "가격은 0보다 커야 합니다" }
            require(quantity > 0) { "수량은 0보다 커야 합니다" }

            val now = LocalDateTime.now()
            return OrderDetail(
                id = OrderDetailId.NEW,
                price = price,
                quantity = quantity,
                wrap = wrap,
                orderStatus = OrderStatus.PENDING,
                wrapping = wrapping,
                product = ProductMapper.toEntity(product, CategoryMapper
                    .toEntity(
                        product.category,
                        parentEntity = CategoryMapper
                            .toEntity(
                                product.category.parentCategory,
                                parentEntity = TODO()
                            )
                    )),
                order = order,
                createAt = now,
                updateAt = now
            )
        }
    }

    fun changeStatus(newStatus: OrderStatus): OrderDetail {
        require(orderStatus.canTransitionTo(newStatus)) {
            "${orderStatus.displayName}에서 ${newStatus.displayName}(으)로 변경할 수 없습니다"
        }
        return copy(
            orderStatus = newStatus,
            updateAt = LocalDateTime.now()
        )
    }

    fun confirm(): OrderDetail = changeStatus(OrderStatus.CONFIRMED)
    fun startProcessing(): OrderDetail = changeStatus(OrderStatus.PROCESSING)
    fun startShipping(): OrderDetail = changeStatus(OrderStatus.SHIPPING)
    fun completeShipping(): OrderDetail = changeStatus(OrderStatus.SHIPPED)
    fun completeDelivery(): OrderDetail = changeStatus(OrderStatus.DELIVERED)
    fun cancel(): OrderDetail = changeStatus(OrderStatus.CANCELLED)
    fun refund(): OrderDetail = changeStatus(OrderStatus.REFUNDED)
    fun returnOrder(): OrderDetail = changeStatus(OrderStatus.RETURNED)

    fun canModify(): Boolean = orderStatus.isModifiable()
    fun canCancel(): Boolean = orderStatus.isCancellable()
    fun canReturn(): Boolean = orderStatus.isReturnable()
    fun canReview(): Boolean = orderStatus == OrderStatus.DELIVERED

    fun belongsToUser(userId: UserId): Boolean {
        return order.user?.id == userId
    }

    fun belongsToUser(userId: Long): Boolean {
        return order.user?.id?.value == userId
    }

    fun getTotalPrice(): Int {
        val wrappingPrice = if (wrap && wrapping != null) wrapping.price else 0
        return (price * quantity) + wrappingPrice
    }

    fun isDelivered(): Boolean {
        return orderStatus.isDelivered()
    }
}
