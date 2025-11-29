package com.ps.app.user.domain

@JvmInline
value class UserId(val value: Long) {
    companion object {
        val NEW = UserId(0L)
    }

    fun isNew(): Boolean = value == 0L
}
