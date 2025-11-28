package com.ps.app.products.adapter.out.persistence

import com.ps.app.products.application.port.out.BookPort
import com.ps.app.products.domain.*
import com.ps.app.products.exception.ProductNotFoundException
import com.ps.app.products.exception.PublisherNotFoundException
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class BookPersistenceAdapter(
    private val bookJpaRepository: BookJpaRepository,
    private val publisherJpaRepository: PublisherJpaRepository,
    private val productJpaRepository: ProductJpaRepository
) : BookPort {

    @Transactional
    override fun save(book: Book): Book {
        val publisherEntity = publisherJpaRepository.findById(book.publisherId.value)

        val productEntity = findProduct(book.productId)

        val entity = BookEntity.fromDomain(book, publisherEntity, productEntity)
        val saved = bookJpaRepository.save(entity)
        return saved.toDomain()
    }

    override fun findById(id: BookId): Book? {
        return bookJpaRepository.findById(id.value)?.toDomain()
    }

    override fun findByIsbn(isbn: ISBN): Book? {
        return bookJpaRepository.findByIsbn(isbn.value)?.toDomain()
    }

    override fun findByProductId(productId: ProductId): List<Book> {
        return bookJpaRepository.findByProductId(productId.value)
            .map { it.toDomain() }
    }

    override fun findUnregisteredBooks(): List<Book> {
        return bookJpaRepository.findByProductIdIsNull()
            .map { it.toDomain() }
    }

    override fun findAll(): List<Book> {
        return bookJpaRepository.findAll()
            .map { it.toDomain() }
    }

    @Transactional
    override fun delete(book: Book) {
        bookJpaRepository.deleteById(book.id.value)
    }

    private fun findOrCreatePublisher(publisher: Publisher): PublisherEntity {
        return (if (publisher.isNew()) {
            val newPublisherEntity = PublisherEntity.fromDomain(publisher)
            publisherJpaRepository.save(newPublisherEntity)
        } else {
            publisherJpaRepository.findById(publisher.id.value)
        }) as PublisherEntity
    }

    private fun findProduct(productId: ProductId?): ProductEntity? {
        return productId?.let { id ->
            productJpaRepository.findById(id.value)
                ?: throw ProductNotFoundException("상품을 찾을 수 없습니다: $id")
        }
    }
}
