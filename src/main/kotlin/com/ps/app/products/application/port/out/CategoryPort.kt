package com.ps.app.products.application.port.out

import com.ps.app.products.domain.Category

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
