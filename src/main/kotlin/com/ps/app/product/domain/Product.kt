package com.ps.app.product.domain

import com.ps.app.product.domain.constant.StockStatus
import java.time.LocalDate

class Product(
    val id: Int = 0,
    stock: Int,
    val productName: String,
    val description: String?,
    val price: Int,
    val forwardDate: LocalDate?,
    score: Int,
    val thumbnailPath: String?,
    stockStatus: StockStatus,
    val category: Category
) {
    var stock: Int = stock
        private set

    var score: Int = score
        private set

    var stockStatus: StockStatus = stockStatus
        private set

    fun increaseStock(amount: Int) {
        require(amount > 0) { "증가량은 0보다 커야 합니다" }
        stock += amount
    }

    fun decreaseStock(amount: Int) {
        require(amount > 0) { "감소량은 0보다 커야 합니다" }
        require(stock >= amount) { "재고부족" }

        stock -= amount
        if (stock == 0) {
            stockStatus = StockStatus.OUT_OF_STOCK
        }
    }

    fun updateScore(newScore: Int) {
        require(newScore >= 0) { "점수는 0 이상이어야 합니다" }
        score = newScore
    }

    companion object {
        fun create(
            stock: Int,
            productName: String,
            description: String?,
            price: Int,
            forwardDate: LocalDate?,
            score: Int,
            thumbnailPath: String?,
            stockStatus: StockStatus,
            category: Category
        ): Product {
            require(stock >= 0) { "재고는 0 이상이어야 합니다" }
            require(productName.isNotBlank()) { "상품명은 필수입니다" }
            require(price > 0) { "가격은 0보다 커야 합니다" }
            require(score >= 0) { "점수는 0 이상이어야 합니다" }

            return Product(
                stock = stock,
                productName = productName,
                description = description,
                price = price,
                forwardDate = forwardDate,
                score = score,
                thumbnailPath = thumbnailPath,
                stockStatus = stockStatus,
                category = category
            )
        }
    }
}
