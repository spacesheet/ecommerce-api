package com.ps.app.products.domain

@JvmInline
value class ISBN(val value: String) {
    init {
        require(value.length == 13) { "ISBN은 13자리여야 합니다" }
        require(value.all { it.isDigit() }) { "ISBN은 숫자만 포함해야 합니다" }
    }
}
