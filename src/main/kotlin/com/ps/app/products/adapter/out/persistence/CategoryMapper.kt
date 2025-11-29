package com.ps.app.products.adapter.out.persistence

import com.ps.app.products.domain.Category
import com.ps.app.products.domain.CategoryId

object CategoryMapper {

    fun toDomain(entity: CategoryEntity): Category {
        val parentCategory = entity.parentCategory?.let { parent ->
            toDomainSimple(parent) // 순환 참조 방지
        }

        return Category(
            id = CategoryId(entity.id),
            name = entity.name,
            parentCategory = parentCategory,
            subCategories = emptyList() // 필요시 별도 로딩
        )
    }

    fun toDomainSimple(entity: CategoryEntity): Category {
        return Category(
            id = CategoryId(entity.id),
            name = entity.name,
            parentCategory = null,
            subCategories = emptyList()
        )
    }

    fun toEntity(domain: Category, parentEntity: CategoryEntity?): CategoryEntity {
        return CategoryEntity(
            id = domain.id.value,
            name = domain.name,
            parentCategory = parentEntity
        )
    }

    fun toDomainList(entities: List<CategoryEntity>): List<Category> {
        return entities.map { toDomain(it) }
    }
}
