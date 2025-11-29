package com.ps.app.products.adapter.out.persistence

import com.ps.app.products.domain.Product
import com.ps.app.products.domain.constant.SalesStatus
import com.ps.app.products.domain.constant.StockStatus
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

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
    val price: BigDecimal,

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
    val productTags: MutableList<ProductTagEntity> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    val salesStatus: SalesStatus,

    val discountRate: Double? = null,

    @CreatedDate
    val createAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    val updateAt: LocalDateTime = LocalDateTime.now()
)
