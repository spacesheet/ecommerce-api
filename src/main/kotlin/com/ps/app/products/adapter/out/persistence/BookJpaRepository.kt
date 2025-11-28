package com.ps.app.products.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BookJpaRepository : JpaRepository<BookEntity, Long> {
    // ISBN으로 조회
    fun findByIsbn(isbn: String): BookEntity?

    // Product ID로 조회
    fun findByProductId(productId: Long): List<BookEntity>

    // 제목으로 조회
    fun findByTitle(title: String): BookEntity?

    // 제목 검색 (부분 일치)
    fun findByTitleContaining(keyword: String): List<BookEntity>

    // 출판사 ID로 조회
    fun findByPublisherId(publisherId: Int): List<BookEntity>

    // ISBN 존재 여부
    fun existsByIsbn(isbn: String): Boolean

    // Product가 없는 책 조회
    fun findByProductIsNull(): List<BookEntity>

    // Product가 있는 책 조회
    fun findByProductIsNotNull(): List<BookEntity>

    fun findByProductIdIsNull(): List<BookEntity>  // 미등록 도서

    // 출판일 범위로 조회
    @Query("""
        SELECT b FROM BookEntity b 
        WHERE b.publishDate BETWEEN :startDate AND :endDate
    """)
    fun findByPublishDateBetween(startDate: java.time.LocalDate, endDate: java.time.LocalDate): List<BookEntity>

    // 저자 ID로 책 조회
    @Query("""
        SELECT DISTINCT b 
        FROM BookEntity b 
        JOIN b.bookAuthors ba 
        WHERE ba.pk.authorId = :authorId
    """)
    fun findByAuthorId(authorId: Int): List<BookEntity>

    // 제목과 출판사로 조회
    fun findByTitleAndPublisherId(title: String, publisherId: Int): BookEntity?
}
