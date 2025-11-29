package com.ps.app.cart.adapter.`in`.web.dto

data class WishlistProductInfo(
    val productId: Long,
    val productName: String,
    val price: Int,
    val thumbnailPath: String?,
    val canWrap: Boolean
)
