package com.ps.app.products.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface PublisherJpaRepository : JpaRepository<PublisherEntity, Int> {
    fun findByName(name: String): PublisherEntity?
    fun findById(id: Long)
}
