package com.ps.app.products.adapter.out.persistence


import org.springframework.data.jpa.repository.JpaRepository

interface TagJpaRepository : JpaRepository<TagEntity, Int> {
    fun findByName(name: String): TagEntity?
}
