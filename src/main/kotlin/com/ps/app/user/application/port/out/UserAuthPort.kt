package com.ps.app.user.application.port.out

import com.ps.app.user.domain.*

interface LoadUserAuthPort {
    fun findById(id: Long): UserAuth?
    fun findByProviderAndProvideId(provider: AuthProvider, provideId: ProvideId): UserAuth?
//    fun findByUserId(userId: UserId): List<UserAuth>
}

interface SaveUserAuthPort {
    fun save(userAuth: UserAuth): UserAuth
}

interface DeleteUserAuthPort {
    fun deleteById(id: UserAuthId)
}
