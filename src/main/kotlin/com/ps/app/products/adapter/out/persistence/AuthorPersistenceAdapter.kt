package com.ps.app.products.adapter.out.persistence

import com.ps.app.products.application.port.out.AuthorPort
import com.ps.app.products.domain.Author
import org.springframework.stereotype.Repository

@Repository
class AuthorPersistenceAdapter(
    private val authorJpaRepository: AuthorJpaRepository,
    private val bookAuthorJpaRepository: BookAuthorJpaRepository
) : AuthorPort {

    override fun save(author: Author): Author {
        val entity = AuthorEntity.fromDomain(author)
        val saved = authorJpaRepository.save(entity)
        return saved.toDomain()
    }

    override fun findById(id: Int): Author? {
        return authorJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findByName(name: String): Author? {
        return authorJpaRepository.findByName(name)?.toDomain()
    }

    override fun findByNameContaining(keyword: String): List<Author> {
        return authorJpaRepository.findByNameContaining(keyword)
            .map { it.toDomain() }
    }

    override fun findAll(): List<Author> {
        return authorJpaRepository.findAll().map { it.toDomain() }
    }

//    override fun findAll(page: Int, size: Int): List<Author> {
//        val pageable = PageRequest.of(page, size)
//        return authorJpaRepository.findAll(pageable)
//            .content
//            .map { it.toDomain() }
//    }

    override fun delete(author: Author) {
        authorJpaRepository.deleteById(author.id)
    }

    override fun deleteById(id: Int) {
        authorJpaRepository.deleteById(id)
    }

    override fun existsById(id: Int): Boolean {
        return authorJpaRepository.existsById(id)
    }

    override fun existsByName(name: String): Boolean {
        return authorJpaRepository.existsByName(name)
    }

    override fun findAllByIds(ids: List<Int>): List<Author> {
        return authorJpaRepository.findAllById(ids).map { it.toDomain() }
    }

    override fun count(): Long {
        return authorJpaRepository.count()
    }

//    override fun findByBookId(bookId: Long): List<Author> {
//        return bookAuthorJpaRepository.findByPkBookId(bookId)
//            .map { it.author.toDomain() }
//    }
}
