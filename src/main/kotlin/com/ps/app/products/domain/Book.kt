package com.ps.app.products.domain

import java.time.LocalDate

// 책 정보 (알라딘 API로부터)
data class Book(
    val id: BookId,
    val productId: ProductId?, // nullable!
    val isbn: ISBN,
    val title: String,
    val author: String,
    val publisherId: PublisherId,
    val publishDate: LocalDate?,
    val description: String?,
    val coverImageUrl: String?,
    val category: String?
) {
    fun isRegisteredAsProduct(): Boolean = productId != null

    fun attachProduct(productId: ProductId): Book {
        require(this.productId == null) { "이미 상품으로 등록된 도서입니다." }
        return copy(productId = productId)
    }


//    companion object {
//        fun create(
//            title: String,
//            description: String?,
//            isbn: String,
//            publisher: Publisher,
//            publishDate: String
//        ): Book {
//            require(title.isNotBlank()) { "제목은 필수입니다" }
//            require(isbn.length == 13) { "ISBN은 13자리여야 합니다" }
//
//            return Book(
//                title = title,
//                description = description,
//                isbn = isbn,
//                publisher = publisher,
//                publishDate = LocalDate.parse(publishDate)
//            )
//        }
//    }
}
