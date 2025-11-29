package com.ps.app.cart.adapter.`in`.web.dto

import java.math.BigDecimal

data class CartDetailResponse(
    val id: Long,
    val productId: Long,
    val productName: String,
    val quantity: Int,
    val price: BigDecimal,
    val canWrap: Boolean,
    val thumbnailPath: String?,
    val categoryId: Int
)
