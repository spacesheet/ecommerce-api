package com.ps.app.user.adapter.out.persistence

import jakarta.persistence.*

@Entity
@Table(
    name = "user_auth",
    indexes = [
        Index(name = "unique_provider_id", columnList = "provider, provideId", unique = true),
        Index(name = "index_provide_Id", columnList = "provideId")
    ]
)
class UserAuthJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "user_id", nullable = false)
    var userId: Long,

    @Column(name = "provider", nullable = false, length = 20)
    var provider: String,

    @Column(name = "provide_Id", nullable = false, columnDefinition = "binary(16)")
    var provideId: ByteArray
)
