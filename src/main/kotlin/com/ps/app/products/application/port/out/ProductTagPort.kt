package com.ps.app.products.application.port.out

import com.ps.app.products.domain.ProductTag

interface ProductTagPort {
    fun save(productTag: ProductTag): ProductTag
    fun findByProductId(productId: Int): List<ProductTag>
    fun findByTagId(tagId: Int): List<ProductTag>
    fun deleteByProductIdAndTagId(productId: Int, tagId: Int)
    fun deleteAllByProductId(productId: Int)
}
