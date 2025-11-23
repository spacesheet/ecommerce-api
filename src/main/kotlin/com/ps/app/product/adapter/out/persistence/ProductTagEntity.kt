package com.ps.app.product.adapter.out.persistence

import com.ps.app.product.adapter.out.persistence.pk.ProductTagPK
import com.ps.app.product.domain.ProductTag
import jakarta.persistence.*

@Entity
@Table(
    name = "product_tag",
    uniqueConstraints = [UniqueConstraint(columnNames = ["product_id", "tag_id"])]
)
class ProductTagEntity(
    @EmbeddedId
    val pk: ProductTagPK,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    val product: ProductEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    val tag: TagEntity
) {
    fun toDomain(): ProductTag = ProductTag(
        productId = pk.productId,
        tagId = pk.tagId,
        product = product.toDomain(),
        tag = tag.toDomain()
    )

    companion object {
        fun fromDomain(
            domain: ProductTag,
            productEntity: ProductEntity,
            tagEntity: TagEntity
        ): ProductTagEntity {
            return ProductTagEntity(
                pk = ProductTagPK(domain.productId, domain.tagId),
                product = productEntity,
                tag = tagEntity
            )
        }
    }
}
