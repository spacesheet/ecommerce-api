package com.ps.app.product.application.port.out

import com.ps.app.product.domain.BookAuthor

interface BookAuthorPort {
    fun save(bookAuthor: BookAuthor): BookAuthor
    fun findByBookId(bookId: Long): List<BookAuthor>
    fun findByAuthorId(authorId: Long): List<BookAuthor>
    fun deleteByBookIdAndAuthorId(bookId: Long, authorId: Int)
    fun deleteAllByBookId(bookId: Long)
}
