package com.ps.app.product.adapter.out.persistence

import com.ps.app.product.adapter.out.persistence.pk.ProductTagPK
import com.ps.app.product.application.port.out.ProductTagPort
import com.ps.app.product.domain.ProductTag
import org.springframework.stereotype.Repository

@Repository
class ProductTagPersistenceAdapter(
    private val productTagJpaRepository: ProductTagJpaRepository,
    private val productJpaRepository: ProductJpaRepository,
    private val tagJpaRepository: TagJpaRepository
) : ProductTagPort {

    override fun save(productTag: ProductTag): ProductTag {
        val productEntity = productJpaRepository.findById(productTag.productId)
            .orElseThrow { IllegalArgumentException("상품을 찾을 수 없습니다") }

        val tagEntity = tagJpaRepository.findById(productTag.tagId)
            .orElseThrow { IllegalArgumentException("태그를 찾을 수 없습니다") }

        val entity = ProductTagEntity.fromDomain(productTag, productEntity, tagEntity)
        val saved = productTagJpaRepository.save(entity)
        return saved.toDomain()
    }

    override fun findByProductId(productId: Int): List<ProductTag> {
        return productTagJpaRepository.findByPkProductId(productId).map { it.toDomain() }
    }

    override fun findByTagId(tagId: Int): List<ProductTag> {
        return productTagJpaRepository.findByPkTagId(tagId).map { it.toDomain() }
    }

    override fun deleteByProductIdAndTagId(productId: Int, tagId: Int) {
        productTagJpaRepository.deleteById(ProductTagPK(productId, tagId))
    }

    override fun deleteAllByProductId(productId: Int) {
        productTagJpaRepository.deleteByPkProductId(productId)
    }
}
