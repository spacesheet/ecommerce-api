package com.ps.app.product.adapter.out.persistence

import com.ps.app.product.adapter.out.persistence.pk.ProductTagPK
import org.springframework.data.jpa.repository.JpaRepository

interface ProductTagJpaRepository : JpaRepository<ProductTagEntity, ProductTagPK> {
    fun findByPkProductId(productId: Int): List<ProductTagEntity>
    fun findByPkTagId(tagId: Int): List<ProductTagEntity>
    fun deleteByPkProductId(productId: Int)
}
