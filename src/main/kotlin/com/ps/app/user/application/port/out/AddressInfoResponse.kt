package com.ps.app.user.application.port.out

data class AddressInfoResponse(
    val id: Long?,
    val address: String,
    val detail: String,
    val zipcode: Int,
    val nation: String,
    val alias: String
)
