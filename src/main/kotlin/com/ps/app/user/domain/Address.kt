package com.ps.app.user.domain

import com.ps.app.user.application.port.out.AddressInfoResponse
import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @NotNull
    @Column(name = "address")
    @Size(min = 1, max = 255)
    var address: String,

    @NotNull
    @Column(name = "detail")
    @Size(min = 1, max = 255)
    var detail: String,

    @NotNull
    @Column(name = "zipcode")
    @Max(99999)
    @Min(0)
    var zipcode: Int,

    @NotNull
    @Column(name = "nation")
    @Size(min = 1, max = 50)
    var nation: String,

    @NotNull
    @Column(name = "alias")
    @Size(min = 1, max = 20)
    var alias: String
) {
    fun toResponse(): AddressInfoResponse {
        return AddressInfoResponse(
            id = this.id,
            address = this.address,
            detail = this.detail,
            zipcode = this.zipcode,
            alias = this.alias,
            nation = this.nation
        )
    }
}
