package com.ps.app.cart.domain

import com.ps.app.user.domain.User
import java.util.UUID

data class Cart(
    val id: Long = 0,
    val user: User?,
    val uuid: UUID
) {
    companion object {
        fun create(user: User?): Cart {
            return Cart(
                user = user,
                uuid = UUID.randomUUID()
            )
        }
    }
}
