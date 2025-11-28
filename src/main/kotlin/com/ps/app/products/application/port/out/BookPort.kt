package com.ps.app.products.application.port.out

import com.ps.app.products.domain.Book
import com.ps.app.products.domain.BookId
import com.ps.app.products.domain.ISBN
import com.ps.app.products.domain.ProductId

interface BookPort {
    fun save(book: Book): Book
    fun findById(id: BookId): Book?
    fun findByIsbn(isbn: ISBN): Book?
    fun findByProductId(productId: ProductId): List<Book>
    fun findUnregisteredBooks(): List<Book>  // productId가 null인 책들
    fun findAll(): List<Book>
    fun delete(book: Book)
}
