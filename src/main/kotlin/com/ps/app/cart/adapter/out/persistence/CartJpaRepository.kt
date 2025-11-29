package com.ps.app.cart.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface CartJpaRepository : JpaRepository<CartEntity, Long> {
    fun findByUuid(uuid: ByteArray): CartEntity?
    fun findByUserId(userId: Long): CartEntity?
}
