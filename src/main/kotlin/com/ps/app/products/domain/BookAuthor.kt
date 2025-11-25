package com.ps.app.products.domain

data class BookAuthor(
    val bookId: Long,
    val authorId: Int,
    val author: Author,
    val book: Book
) {
    companion object {
        fun create(book: Book, author: Author): BookAuthor {
            require(book.id > 0) { "유효한 도서 ID가 필요합니다" }
            require(author.id > 0) { "유효한 저자 ID가 필요합니다" }

            return BookAuthor(
                bookId = book.id,
                authorId = author.id,
                author = author,
                book = book
            )
        }
    }
}
