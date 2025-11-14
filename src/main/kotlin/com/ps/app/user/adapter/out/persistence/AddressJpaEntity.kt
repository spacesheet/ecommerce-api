package com.ps.app.user.adapter.out.persistence

import jakarta.persistence.*

@Entity
@Table(name = "address")
class AddressJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "user_id", nullable = false)
    var userId: Long,

    @Column(name = "address", nullable = false, length = 255)
    var address: String,

    @Column(name = "detail", nullable = false, length = 255)
    var detail: String,

    @Column(name = "zipcode", nullable = false)
    var zipcode: Int,

    @Column(name = "nation", nullable = false, length = 50)
    var nation: String,

    @Column(name = "alias", nullable = false, length = 20)
    var alias: String
)
