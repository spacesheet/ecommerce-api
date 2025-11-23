package com.ps.app.product.application.port.out

import com.ps.app.product.domain.Category

interface CategoryPort {
    fun save(category: Category): Category
    fun findById(id: Int): Category?
    fun findByName(name: String): Category?
    fun findRootCategories(): List<Category>
    fun findSubCategoriesByParentId(parentId: Int): List<Category>
    fun findAll(): List<Category>
    fun delete(category: Category)
    fun existsById(id: Int): Boolean
}
