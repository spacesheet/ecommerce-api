package com.ps.app.user.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressJpaRepository : JpaRepository<AddressJpaEntity, Long> {
    fun findByUserId(userId: Long): List<AddressJpaEntity>
}
