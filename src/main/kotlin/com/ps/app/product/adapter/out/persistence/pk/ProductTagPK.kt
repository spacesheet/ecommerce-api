package com.ps.app.product.adapter.out.persistence.pk

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class ProductTagPK(
    @Column(name = "product_id")
    val productId: Int = 0,

    @Column(name = "tag_id")
    val tagId: Int = 0
) : Serializable
