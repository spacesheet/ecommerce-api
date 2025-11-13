package com.ps.app.user.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(
    name = "user_auth",
    indexes = [
        Index(name = "unique_provider_id", columnList = "provider, provideId", unique = true),
        Index(name = "index_provide_Id", columnList = "provideId")
    ]
)
class UserAuth(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @NotNull
    @Size(max = 20)
    @Column(name = "provider")
    var provider: String,

    @NotNull
    @Column(name = "`provide_Id`", nullable = false, columnDefinition = "binary(16)")
    var provideId: ByteArray
)
