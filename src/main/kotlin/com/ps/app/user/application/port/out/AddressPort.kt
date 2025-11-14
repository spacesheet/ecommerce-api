package com.ps.app.user.application.port.out

import com.ps.app.user.domain.*

interface LoadAddressPort {
    fun findById(id: AddressId): Address?
    fun findByUserId(userId: UserId): List<Address>
}

interface SaveAddressPort {
    fun save(address: Address): Address
}

interface DeleteAddressPort {
    fun deleteById(id: AddressId)
}
