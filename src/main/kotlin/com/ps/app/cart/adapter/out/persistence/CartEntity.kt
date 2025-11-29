package com.ps.app.cart.adapter.out.persistence


import com.ps.app.user.adapter.out.persistence.UserEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "cart")
class CartEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: UserEntity?,

    @NotNull
    @Column(name = "`uuid`", nullable = false, unique = true, columnDefinition = "binary(16)")
    val uuid: ByteArray
)
