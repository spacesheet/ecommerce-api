package com.ps.app.user.domain

// 도메인 엔티티 (집합 루트)
data class Address(
    val id: AddressId? = null,
    val userId: UserId,
    val address: FullAddress,
    val detail: AddressDetail,
    val zipcode: Zipcode,
    val nation: Nation,
    val alias: AddressAlias
) {
    companion object {
        fun create(
            userId: UserId,
            address: FullAddress,
            detail: AddressDetail,
            zipcode: Zipcode,
            nation: Nation,
            alias: AddressAlias
        ): Address {
            return Address(
                userId = userId,
                address = address,
                detail = detail,
                zipcode = zipcode,
                nation = nation,
                alias = alias
            )
        }
    }

    fun changeAddress(newAddress: FullAddress, newDetail: AddressDetail): Address {
        return copy(address = newAddress, detail = newDetail)
    }

    fun changeAlias(newAlias: AddressAlias): Address {
        return copy(alias = newAlias)
    }
}

// Value Objects
@JvmInline
value class AddressId(val value: Long)

@JvmInline
value class UserId(val value: Long)

@JvmInline
value class FullAddress(val value: String) {
    init {
        require(value.isNotBlank()) { "Address cannot be blank" }
        require(value.length <= 255) { "Address must be 255 characters or less" }
    }
}

@JvmInline
value class AddressDetail(val value: String) {
    init {
        require(value.isNotBlank()) { "Address detail cannot be blank" }
        require(value.length <= 255) { "Address detail must be 255 characters or less" }
    }
}

@JvmInline
value class Zipcode(val value: Int) {
    init {
        require(value in 0..99999) { "Zipcode must be between 0 and 99999" }
    }
}

@JvmInline
value class Nation(val value: String) {
    init {
        require(value.isNotBlank()) { "Nation cannot be blank" }
        require(value.length <= 50) { "Nation must be 50 characters or less" }
    }
}

@JvmInline
value class AddressAlias(val value: String) {
    init {
        require(value.isNotBlank()) { "Alias cannot be blank" }
        require(value.length <= 20) { "Alias must be 20 characters or less" }
    }
}
