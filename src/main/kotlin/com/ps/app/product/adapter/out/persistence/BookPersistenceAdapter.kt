package com.ps.app.product.adapter.out.persistence

import com.ps.app.product.application.port.out.BookPort
import com.ps.app.product.domain.Book
import org.springframework.stereotype.Repository

@Repository
class BookPersistenceAdapter(
    private val bookJpaRepository: BookJpaRepository,
    private val publisherJpaRepository: PublisherJpaRepository,
    private val productJpaRepository: ProductJpaRepository
) : BookPort {

    override fun save(book: Book): Book {
        val publisherEntity = publisherJpaRepository.findById(book.publisher.id)
            .orElseThrow { IllegalArgumentException("출판사를 찾을 수 없습니다") }

        val productEntity = book.product?.let { product ->
            productJpaRepository.findById(product.id)
                .orElseThrow { IllegalArgumentException("상품을 찾을 수 없습니다") }
        }

        val entity = BookEntity.fromDomain(book, publisherEntity, productEntity)
        val saved = bookJpaRepository.save(entity)
        return saved.toDomain()
    }

    override fun findById(id: Long): Book? {
        return bookJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findByIsbn(isbn: String): Book? {
        return bookJpaRepository.findByIsbn(isbn)?.toDomain()
    }

    override fun findByProductId(productId: Int): List<Book> {
        return bookJpaRepository.findByProductId(productId).map { it.toDomain() }
    }

    override fun findAll(): List<Book> {
        return bookJpaRepository.findAll().map { it.toDomain() }
    }

    override fun delete(book: Book) {
        bookJpaRepository.deleteById(book.id)
    }
}
