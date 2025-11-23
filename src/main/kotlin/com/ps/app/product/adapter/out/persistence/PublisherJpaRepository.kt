package com.ps.app.product.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface PublisherJpaRepository : JpaRepository<PublisherEntity, Int> {
    fun findByName(name: String): PublisherEntity?
}
