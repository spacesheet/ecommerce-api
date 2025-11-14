package com.ps.app.user.domain

// 도메인 엔티티
data class UserAuth(
    val id: UserAuthId? = null,
    val userId: UserId,
    val provider: AuthProvider,
    val provideId: ProvideId
) {
    companion object {
        fun create(userId: UserId, provider: AuthProvider, provideId: ProvideId): UserAuth {
            return UserAuth(
                userId = userId,
                provider = provider,
                provideId = provideId
            )
        }
    }
}

// Value Objects
@JvmInline
value class UserAuthId(val value: Long)

@JvmInline
value class UserId(val value: Long)

@JvmInline
value class ProvideId(val value: String) {
    init {
        require(value.isNotBlank()) { "Provider ID cannot be blank" }
    }
}

enum class AuthProvider(val value: String) {
    GOOGLE("google"),
    KAKAO("kakao"),
    NAVER("naver"),
    APPLE("apple");

    companion object {
        fun from(value: String): AuthProvider {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown provider: $value")
        }
    }
}
