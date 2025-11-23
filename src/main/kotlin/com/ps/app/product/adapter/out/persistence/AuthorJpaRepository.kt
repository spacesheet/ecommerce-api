package com.ps.app.product.adapter.out.persistence

import com.ps.app.product.domain.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface AuthorJpaRepository : JpaRepository<AuthorEntity, Int> {
    /**
     * 이름으로 저자 조회 (정확히 일치)
     */
    fun findByName(name: String): AuthorEntity?

    /**
     * 이름으로 저자 검색 (부분 일치)
     */
    fun findByNameContaining(keyword: String): List<AuthorEntity>

    /**
     * 이름 존재 여부 확인
     */
    fun existsByName(name: String): Boolean

    /**
     * 특정 도서의 저자 조회 (QueryDSL 또는 JPQL 사용)
     */
    @Query("""
        SELECT DISTINCT a 
        FROM AuthorEntity a 
        JOIN a.bookAuthors ba 
        WHERE ba.book.id = :bookId
    """)
    fun findByBookId(bookId: Long): List<AuthorEntity>

    /**
     * 저자 이름으로 정렬하여 조회
     */
    fun findAllByOrderByNameAsc(): List<AuthorEntity>

    fun findById(id: Long) : Optional<Author>
    fun deleteById(id: Long) : Optional<Author>
}
