package com.ps.app.product.application.port.out

import com.ps.app.product.domain.ProductTag

interface ProductTagPort {
    fun save(productTag: ProductTag): ProductTag
    fun findByProductId(productId: Int): List<ProductTag>
    fun findByTagId(tagId: Int): List<ProductTag>
    fun deleteByProductIdAndTagId(productId: Int, tagId: Int)
    fun deleteAllByProductId(productId: Int)
}
