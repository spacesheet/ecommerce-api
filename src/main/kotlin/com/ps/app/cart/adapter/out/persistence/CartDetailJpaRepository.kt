package com.ps.app.cart.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface CartDetailJpaRepository : JpaRepository<CartDetailEntity, Long> {
    fun findByCartId(cartId: Long): List<CartDetailEntity>
}
