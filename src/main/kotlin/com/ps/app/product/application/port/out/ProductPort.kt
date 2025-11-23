package com.ps.app.product.application.port.out

import com.ps.app.product.domain.Product

interface ProductPort {
    fun save(product: Product): Product
    fun findById(id: Int): Product?
    fun findAll(): List<Product>
    fun delete(product: Product)
    fun existsById(id: Int): Boolean
}
