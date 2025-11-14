package com.ps.app.user.adapter.out.persistence

import com.ps.app.user.application.port.out.*
import com.ps.app.user.domain.*
import org.springframework.stereotype.Component

@Component
class AddressPersistenceAdapter(
    private val addressJpaRepository: AddressJpaRepository
) : LoadAddressPort, SaveAddressPort, DeleteAddressPort {

    override fun findById(id: AddressId): Address? {
        return addressJpaRepository.findById(id.value)
            .map { AddressPersistenceMapper.mapToDomain(it) }
            .orElse(null)
    }

    override fun findByUserId(userId: UserId): List<Address> {
        return addressJpaRepository.findByUserId(userId.value)
            .map { AddressPersistenceMapper.mapToDomain(it) }
    }

    override fun save(address: Address): Address {
        val entity = AddressPersistenceMapper.mapToEntity(address)
        val savedEntity = addressJpaRepository.save(entity)
        return AddressPersistenceMapper.mapToDomain(savedEntity)
    }

    override fun deleteById(id: AddressId) {
        addressJpaRepository.deleteById(id.value)
    }
}
