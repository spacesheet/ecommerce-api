package com.ps.app.user.adapter.out.persistence

import com.ps.app.user.domain.*

object AddressPersistenceMapper {

    fun mapToDomain(entity: AddressJpaEntity): Address {
        return Address(
            id = entity.id?.let { AddressId(it) },
            userId = UserId(entity.userId),
            address = FullAddress(entity.address),
            detail = AddressDetail(entity.detail),
            zipcode = Zipcode(entity.zipcode),
            nation = Nation(entity.nation),
            alias = AddressAlias(entity.alias)
        )
    }

    fun mapToEntity(domain: Address): AddressJpaEntity {
        return AddressJpaEntity(
            id = domain.id?.value,
            userId = domain.userId.value,
            address = domain.address.value,
            detail = domain.detail.value,
            zipcode = domain.zipcode.value,
            nation = domain.nation.value,
            alias = domain.alias.value
        )
    }
}
