package com.ps.app.user.domain

enum class UserStatus(val description: String) {
    ACTIVE("활성"),
    DORMANT("휴면"),
    WITHDRAW("탈퇴");

    fun isAvailable(): Boolean {
        return this == ACTIVE
    }
}
