package com.ps.app.products.domain

import java.time.LocalDate

class Book(
    val id: Long = 0L,
    val title: String,
    val description: String?,
    val isbn: String,
    val publisher: Publisher,
    val publishDate: LocalDate,
    product: Product? = null
) {
    var product: Product? = product
        private set

    fun assignProduct(product: Product) {
        this.product = product
    }

    fun removeProduct() {
        this.product = null
    }

    companion object {
        fun create(
            title: String,
            description: String?,
            isbn: String,
            publisher: Publisher,
            publishDate: String
        ): Book {
            require(title.isNotBlank()) { "제목은 필수입니다" }
            require(isbn.length == 13) { "ISBN은 13자리여야 합니다" }

            return Book(
                title = title,
                description = description,
                isbn = isbn,
                publisher = publisher,
                publishDate = LocalDate.parse(publishDate)
            )
        }
    }
}
