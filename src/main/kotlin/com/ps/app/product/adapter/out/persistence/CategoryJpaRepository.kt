package com.ps.app.product.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface CategoryJpaRepository : JpaRepository<CategoryEntity, Int> {
    fun findByName(name: String): CategoryEntity?
    fun findByParentCategoryIsNull(): List<CategoryEntity>
    fun findByParentCategoryId(parentId: Int): List<CategoryEntity>
}
