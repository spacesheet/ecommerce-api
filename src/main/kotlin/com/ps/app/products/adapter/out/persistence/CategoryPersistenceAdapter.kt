package com.ps.app.products.adapter.out.persistence

import com.ps.app.products.application.port.out.CategoryPort
import com.ps.app.products.domain.Category
import org.springframework.stereotype.Repository

@Repository
class CategoryPersistenceAdapter(
    private val categoryJpaRepository: CategoryJpaRepository
) : CategoryPort {

    override fun save(category: Category): Category {
        val parentEntity = category.parentCategory?.let { parent ->
            categoryJpaRepository.findById(parent.id)
                .orElseThrow { IllegalArgumentException("부모 카테고리를 찾을 수 없습니다") }
        }

        val entity = CategoryEntity.fromDomain(category, parentEntity)
        val saved = categoryJpaRepository.save(entity)
        return saved.toDomain()
    }

    override fun findById(id: Int): Category? {
        return categoryJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findByName(name: String): Category? {
        return categoryJpaRepository.findByName(name)?.toDomain()
    }

    override fun findRootCategories(): List<Category> {
        return categoryJpaRepository.findByParentCategoryIsNull().map { it.toDomain() }
    }

    override fun findSubCategoriesByParentId(parentId: Int): List<Category> {
        return categoryJpaRepository.findByParentCategoryId(parentId).map { it.toDomain() }
    }

    override fun findAll(): List<Category> {
        return categoryJpaRepository.findAll().map { it.toDomain() }
    }

    override fun delete(category: Category) {
        categoryJpaRepository.deleteById(category.id)
    }

    override fun existsById(id: Int): Boolean {
        return categoryJpaRepository.existsById(id)
    }
}
