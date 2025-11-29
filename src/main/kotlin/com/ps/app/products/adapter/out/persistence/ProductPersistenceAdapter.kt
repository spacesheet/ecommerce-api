package com.ps.app.products.adapter.out.persistence

import com.ps.app.products.application.port.out.ProductPort
import com.ps.app.products.domain.Product
import com.ps.app.products.domain.ProductId
import org.springframework.stereotype.Repository

@Repository
class ProductPersistenceAdapter(
    private val productJpaRepository: ProductJpaRepository,
    private val categoryJpaRepository: CategoryJpaRepository
) : ProductPort {

    override fun save(product: Product): Product {
        val categoryEntity = categoryJpaRepository.findById(product.category.id)
            .orElseThrow { IllegalArgumentException("카테고리를 찾을 수 없습니다") }

        val entity = ProductEntity.fromDomain(product, categoryEntity)
        val saved = productJpaRepository.save(entity)
        return saved.toDomain()
    }

    override fun findById(id: ProductId): Product? {
        return productJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findAll(): List<Product> {
        return productJpaRepository.findAll().map { it.toDomain() }
    }

    override fun delete(product: Product) {
        productJpaRepository.deleteById(product.id.value)
    }

    override fun existsById(id: Int): Boolean {
        return productJpaRepository.existsById(id)
    }
}
