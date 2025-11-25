package com.ps.app.products.adapter.out.persistence

import com.ps.app.products.adapter.out.persistence.pk.BookAuthorPK
import com.ps.app.products.domain.BookAuthor
import com.ps.app.products.application.port.out.BookAuthorPort
import org.springframework.stereotype.Repository

@Repository
class BookAuthorPersistenceAdapter(
    private val bookAuthorJpaRepository: BookAuthorJpaRepository,
    private val authorJpaRepository: AuthorJpaRepository,
    private val bookJpaRepository: BookJpaRepository
) : BookAuthorPort {

    override fun save(bookAuthor: BookAuthor): BookAuthor {
        val authorEntity = authorJpaRepository.findById(bookAuthor.authorId)
            .orElseThrow { IllegalArgumentException("저자를 찾을 수 없습니다") }

        val bookEntity = bookJpaRepository.findById(bookAuthor.bookId)
            .orElseThrow { IllegalArgumentException("책을 찾을 수 없습니다") }

        val bookAuthorEntity = BookAuthorEntity.fromDomain(bookAuthor, authorEntity,
            bookEntity)
        val saved = bookAuthorJpaRepository.save(bookAuthorEntity)
        return saved.toDomain()
    }

    override fun findByBookId(bookId: Long): List<BookAuthor> {
        return bookAuthorJpaRepository.findByPkBookId(bookId).map { it.toDomain() }
    }

    override fun findByAuthorId(authorId: Long): List<BookAuthor> {
        return bookAuthorJpaRepository.findByPkAuthorId(authorId).map { it.toDomain() }
    }

    override fun deleteByBookIdAndAuthorId(bookId: Long, authorId: Int) {
        bookAuthorJpaRepository.deleteById(BookAuthorPK(bookId, authorId))
    }

    override fun deleteAllByBookId(bookId: Long) {
        bookAuthorJpaRepository.deleteByPkBookId(bookId)
    }
}
