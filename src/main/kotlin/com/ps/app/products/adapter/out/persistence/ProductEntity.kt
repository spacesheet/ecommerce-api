package com.ps.app.products.adapter.out.persistence

import com.ps.app.products.domain.Product
import com.ps.app.products.domain.constant.StockStatus
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "product")
class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var stock: Int,

    @Column(nullable = false)
    val productName: String,

    @Column(columnDefinition = "text")
    val description: String?,

    @Column(nullable = false)
    val price: Int,

    @Column(name = "forward_date")
    val forwardDate: LocalDate?,

    @Column(nullable = false)
    var score: Int,

    @Column(name = "thumbnail_path")
    val thumbnailPath: String?,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var stockStatus: StockStatus,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: CategoryEntity,

    @OneToMany(mappedBy = "product", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val productTags: MutableList<ProductTagEntity> = mutableListOf()
) {
    fun toDomain(): Product = Product(
        id = id,
        stock = stock,
        productName = productName,
        description = description,
        price = price,
        forwardDate = forwardDate,
        score = score,
        thumbnailPath = thumbnailPath,
        stockStatus = stockStatus,
        category = category.toDomain()
    )

    companion object {
        fun fromDomain(domain: Product, categoryEntity: CategoryEntity): ProductEntity {
            return ProductEntity(
                id = domain.id,
                stock = domain.stock,
                productName = domain.productName,
                description = domain.description,
                price = domain.price,
                forwardDate = domain.forwardDate,
                score = domain.score,
                thumbnailPath = domain.thumbnailPath,
                stockStatus = domain.stockStatus,
                category = categoryEntity
            )
        }
    }
}
