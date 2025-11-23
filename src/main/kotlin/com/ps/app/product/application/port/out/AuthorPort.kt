package com.ps.app.product.application.port.out

import com.ps.app.product.domain.Author


interface AuthorPort {
    // 기본 CRUD
    fun save(author: Author): Author
    fun findById(id: Int): Author?
    fun findAll(): List<Author>
    fun delete(author: Author)
    fun deleteById(id: Int)

    // 조회
    fun findByName(name: String): Author?
    fun findByNameContaining(keyword: String): List<Author>
    fun findAllByIds(ids: List<Int>): List<Author>

    // 존재 여부
    fun existsById(id: Int): Boolean
    fun existsByName(name: String): Boolean

    // 집계
    fun count(): Long
}
