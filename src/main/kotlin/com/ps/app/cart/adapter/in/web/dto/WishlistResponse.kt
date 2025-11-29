package com.ps.app.cart.adapter.`in`.web.dto

data class WishlistResponse(
    val id: Long,
    val userId: Long,
    val productId: Long,
    val productName: String,
    val price: Int,
    val thumbnailPath: String?,
    val canWrap: Boolean
)
