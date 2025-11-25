package com.ps.app.products.adapter.out.persistence

import com.ps.app.products.adapter.out.persistence.pk.BookAuthorPK
import org.springframework.data.jpa.repository.JpaRepository

interface BookAuthorJpaRepository : JpaRepository<BookAuthorEntity, BookAuthorPK> {
    fun findByPkBookId(bookId: Long): List<BookAuthorEntity>
    fun findByPkAuthorId(authorId: Long): List<BookAuthorEntity>
    fun deleteByPkBookId(bookId: Long)
}
