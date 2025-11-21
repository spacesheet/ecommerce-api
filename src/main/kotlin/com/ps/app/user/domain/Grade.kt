package com.ps.app.user.domain

import com.ps.app.user.domain.constant.GradeName

data class Grade(
    val id: Int = 0,
    val name: GradeName,
    val standard: Int,
    val benefit: Double
) {
    init {
        require(standard >= 0) { "Standard must be non-negative" }
        require(benefit >= 0) { "Benefit must be non-negative" }
    }

    fun hasId(): Boolean = id > 0
}
