package com.ps.app.cart.application.port.out

import com.ps.app.cart.domain.Cart
import java.util.UUID

interface CartPort {
    fun findCartById(id: Long): Cart?
    fun findByUuid(uuid: UUID): Cart?
    fun findByUserId(userId: Long): Cart?
    fun save(cart: Cart): Cart
}
