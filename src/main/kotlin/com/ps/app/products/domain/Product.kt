package com.ps.app.products.domain

import com.ps.app.products.domain.constant.SalesStatus
import java.math.BigDecimal

// 판매 상품 정보
data class Product(
    val id: ProductId,
    val name: String,
    val price: Money,
    val stockQuantity: Int,
    val salesStatus: SalesStatus,
    val discountRate: Double?,
    val category: Category,
    val thumbnailPath: String,
    val productTags: MutableSet<ProductTag> = HashSet()
) {
    companion object {
        fun create(
            name: String,
            price: Money,
            stockQuantity: Int,
            discountRate: Double? = null,
            category: Category,
            thumbnailPath: String,
            productTags: MutableSet<ProductTag> = HashSet()
        ): Product {
            require(price.isPositive()) { "가격은 0보다 커야 합니다" }
            require(stockQuantity >= 0) { "재고는 0 이상이어야 합니다" }
            discountRate?.let {
                require(it in 0.0..1.0) { "할인율은 0~1 사이여야 합니다" }
            }

            return Product(
                id = ProductId.NEW,
                name = name,
                price = price,
                stockQuantity = stockQuantity,
                salesStatus = if (stockQuantity > 0) SalesStatus.ON_SALE else SalesStatus.SOLD_OUT,
                discountRate = discountRate,
                category = category,
                thumbnailPath = thumbnailPath,
                productTags = productTags
            )
        }
    }

    fun applyDiscount(): Money? =
        discountRate?.let { rate ->
            Money(price.value * BigDecimal.valueOf(1 - rate))
        }

    fun isNew(): Boolean = id.isNew()

    fun canBeSold(): Boolean =
        salesStatus == SalesStatus.ON_SALE && stockQuantity > 0

    fun getFinalPrice(): Money {
        return discountRate?.let { rate ->
            price * (1 - rate)
        } ?: price
    }

    fun increaseStock(quantity: Int): Product {
        require(quantity > 0) { "증가량은 0보다 커야 합니다" }
        val newQuantity = stockQuantity + quantity
        val newStatus = if (salesStatus == SalesStatus.SOLD_OUT && newQuantity > 0) {
            SalesStatus.ON_SALE
        } else {
            salesStatus
        }
        return copy(stockQuantity = newQuantity, salesStatus = newStatus)
    }

    fun decreaseStock(quantity: Int): Product {
        require(quantity > 0) { "감소량은 0보다 커야 합니다" }
        require(stockQuantity >= quantity) { "재고가 부족합니다" }
        val newQuantity = stockQuantity - quantity
        val newStatus = if (newQuantity == 0) {
            SalesStatus.SOLD_OUT
        } else {
            salesStatus
        }
        return copy(stockQuantity = newQuantity, salesStatus = newStatus)
    }

    fun updatePrice(newPrice: Money): Product {
        require(newPrice.isPositive()) { "가격은 0보다 커야 합니다" }
        return copy(price = newPrice)
    }

    fun applyDiscount(rate: Double): Product {
        require(rate in 0.0..1.0) { "할인율은 0~1 사이여야 합니다" }
        return copy(discountRate = rate)
    }

    fun removeDiscount(): Product {
        return copy(discountRate = null)
    }

    fun discontinue(): Product {
        return copy(salesStatus = SalesStatus.DISCONTINUED)
    }

    fun resume(): Product {
        val newStatus = if (stockQuantity > 0) SalesStatus.ON_SALE else SalesStatus.SOLD_OUT
        return copy(salesStatus = newStatus)
    }

    fun hasTag(tagName: String): Boolean {
        return productTags.any { productTag ->
            productTag.tag.name.equals(tagName, ignoreCase = true)
        }
    }
}
