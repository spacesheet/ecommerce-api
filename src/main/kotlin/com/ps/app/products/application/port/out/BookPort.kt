package com.ps.app.products.application.port.out

import com.ps.app.products.domain.Book

interface BookPort {
    fun save(book: Book): Book
    fun findById(id: Long): Book?
    fun findByIsbn(isbn: String): Book?
    fun findByProductId(productId: Int): List<Book>
    fun findAll(): List<Book>
    fun delete(book: Book)
}
