package com.ps.app.products.adapter.out.persistence

import com.ps.app.products.domain.Product
import com.ps.app.products.domain.ProductId
import com.ps.app.products.domain.Money
import com.ps.app.products.domain.constant.SalesStatus
import java.math.BigDecimal

object ProductMapper {

    /**
     * Entity -> Domain 변환 (전체 정보)
     */
    fun toDomain(entity: ProductEntity?): Product {
        // Category 변환
        val category = CategoryMapper.toDomain(entity.category)

        // ProductTag 변환
        val productTags = entity.productTags.mapNotNull { productTagEntity ->
            runCatching {
                ProductTagMapper.toDomain(productTagEntity)
            }.onFailure { error ->
                println("Failed to map ProductTag: ${error.message}")
            }.getOrNull()
        }?.toSet() ?: emptySet()

        return Product(
            id = ProductId(entity.id),
            productName = entity.productName,
            description = entity.description,
            price = Money(BigDecimal.valueOf(entity.price.toLong())),
            stockQuantity = entity.stockQuantity,
            salesStatus = SalesStatus.valueOf(entity.salesStatus),
            discountRate = entity.discountRate,
            thumbnailPath = entity.thumbnailPath,
            stock = entity.stock,
            category = category,
            productTags = productTags,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    /**
     * Entity -> Domain 변환 (간단한 정보만, ProductTag 제외)
     */
    fun toDomainSimple(entity: ProductEntity): Product {
        val category = CategoryMapper.toDomain(entity.category)

        return Product(
            id = ProductId(entity.id),
            productName = entity.productName,
            description = entity.description,
            price = Money(BigDecimal.valueOf(entity.price.toLong())),
            stockQuantity = entity.stockQuantity,
            salesStatus = SalesStatus.valueOf(entity.salesStatus),
            discountRate = entity.discountRate,
            thumbnailPath = entity.thumbnailPath,
            stock = entity.stock,
            category = category,
            productTags = emptySet(), // 태그 제외
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    /**
     * Domain -> Entity 변환
     */
    fun toEntity(
        domain: Product,
        categoryEntity: CategoryEntity
    ): ProductEntity {
        return ProductEntity(
            id = domain.id.value,
            productName = domain.productName,
            description = domain.description,
            price = domain.price.value.toInt(),
            stockQuantity = domain.stockQuantity,
            salesStatus = domain.salesStatus.name,
            discountRate = domain.discountRate,
            thumbnailPath = domain.thumbnailPath,
            stock = domain.stock,
            category = categoryEntity,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }

    /**
     * 여러 Entity를 Domain으로 변환
     */
    fun toDomainList(entities: List<ProductEntity>): List<Product> {
        return entities.mapNotNull { entity ->
            runCatching {
                toDomain(entity)
            }.onFailure { error ->
                println("Failed to map Product ${entity.id}: ${error.message}")
            }.getOrNull()
        }
    }

    /**
     * 안전한 변환 (실패 시 null 반환)
     */
    fun toDomainSafe(entity: ProductEntity?): Product? {
        return entity?.let {
            runCatching {
                toDomain(it)
            }.onFailure { error ->
                println("Failed to map Product ${entity.id}: ${error.message}")
            }.getOrNull()
        }
    }
}
