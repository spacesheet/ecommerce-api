package com.ps.app.products.domain

class Tag(
    val id: Int = 0,
    val name: String
) {
    companion object {
        fun create(name: String): Tag {
            require(name.isNotBlank()) { "태그명은 필수입니다" }
            require(name.length <= 20) { "태그명은 20자 이하여야 합니다" }
            return Tag(name = name)
        }
    }
}
