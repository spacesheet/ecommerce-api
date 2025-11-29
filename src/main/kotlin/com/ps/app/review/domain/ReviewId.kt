package com.ps.app.review.domain

@JvmInline
value class ReviewId(val value: Int) {
    companion object {
        val NEW = ReviewId(0)
    }

    fun isNew(): Boolean = value == 0
}
