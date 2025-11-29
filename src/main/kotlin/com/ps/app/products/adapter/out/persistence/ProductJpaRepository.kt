package com.ps.app.products.adapter.out.persistence

import com.ps.app.products.domain.constant.StockStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface ProductJpaRepository : JpaRepository<ProductEntity, Int> {
    // 상품명으로 조회
    fun findByProductName(productName: String): ProductEntity?

    // 상품명 검색 (부분 일치)
    fun findByProductNameContaining(keyword: String): List<ProductEntity>

    // 카테고리 ID로 조회
    fun findByCategoryId(categoryId: Int): List<ProductEntity>

    // 재고 상태로 조회
    fun findByStockStatus(stockStatus: StockStatus): List<ProductEntity>

    // 가격 범위로 조회
    fun findByPriceBetween(minPrice: Int, maxPrice: Int): List<ProductEntity>

    // 재고가 있는 상품 조회
    fun findByStockGreaterThan(stock: Int): List<ProductEntity>

    // 카테고리와 재고 상태로 조회
    fun findByCategoryIdAndStockStatus(
        categoryId: Int,
        stockStatus: StockStatus
    ): List<ProductEntity>

    // 점수 순으로 상위 N개 조회
    @Query("""
        SELECT p FROM ProductEntity p 
        ORDER BY p.score DESC
    """)
    fun findTopProducts(limit: Int): List<ProductEntity>
    fun deleteById(id: Long) : Void
    fun findById(id: Long) : Optional<ProductEntity?>
}
