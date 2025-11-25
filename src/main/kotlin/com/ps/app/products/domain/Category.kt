package com.ps.app.products.domain

class Category(
    val id: Int = 0,
    val name: String,
    val parentCategory: Category? = null,
    subCategories: List<Category> = emptyList()
) {
    val subCategories: List<Category> = subCategories.toList()

    /**
     * 현재 카테고리와 모든 하위 카테고리의 ID를 반복문으로 수집
     */
    fun getAllSubCategoryIds(): List<Int> {
        val allSubCategoryIds = mutableListOf<Int>()
        val stack = ArrayDeque<Category>()
        stack.addLast(this)

        while (stack.isNotEmpty()) {
            val currentCategory = stack.removeLast()
            allSubCategoryIds.add(currentCategory.id)

            currentCategory.subCategories.forEach { subCategory ->
                stack.addLast(subCategory)
            }
        }

        return allSubCategoryIds
    }

    /**
     * 재귀 방식으로 모든 하위 카테고리 ID 수집
     */
    fun getAllSubCategoryIdsRecursive(): List<Int> {
        val ids = mutableListOf(id)
        subCategories.forEach { subCategory ->
            ids.addAll(subCategory.getAllSubCategoryIdsRecursive())
        }
        return ids
    }

    /**
     * 현재 카테고리가 루트 카테고리인지 확인
     */
    fun isRoot(): Boolean = parentCategory == null

    /**
     * 현재 카테고리가 리프 카테고리인지 확인
     */
    fun isLeaf(): Boolean = subCategories.isEmpty()

    /**
     * 카테고리의 깊이 계산
     */
    fun getDepth(): Int {
        var depth = 0
        var current = this.parentCategory
        while (current != null) {
            depth++
            current = current.parentCategory
        }
        return depth
    }

    companion object {
        fun createRoot(name: String): Category {
            require(name.isNotBlank()) { "카테고리명은 필수입니다" }
            require(name.length <= 20) { "카테고리명은 20자 이하여야 합니다" }
            return Category(name = name)
        }

        fun createSubCategory(name: String, parentCategory: Category): Category {
            require(name.isNotBlank()) { "카테고리명은 필수입니다" }
            require(name.length <= 20) { "카테고리명은 20자 이하여야 합니다" }
            return Category(name = name, parentCategory = parentCategory)
        }
    }
}
