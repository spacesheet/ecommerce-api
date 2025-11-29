package com.ps.app.cart.application.port.out

import com.ps.app.cart.domain.CartDetail

interface CartDetailPort {
    fun findCartDetailById(id: Long): CartDetail?
    fun findByCartId(cartId: Long): List<CartDetail>
    fun save(cartDetail: CartDetail): CartDetail
    fun delete(cartDetail: CartDetail)
}
