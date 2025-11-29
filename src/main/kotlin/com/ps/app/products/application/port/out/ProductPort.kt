package com.ps.app.products.application.port.out

import com.ps.app.products.domain.Product
import com.ps.app.products.domain.ProductId

interface ProductPort {
    fun save(product: Product): Product
    fun findById(id: ProductId): Product?
    fun findAll(): List<Product>
    fun delete(product: Product)
    fun existsById(id: Int): Boolean
}
