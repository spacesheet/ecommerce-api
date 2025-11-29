package com.ps.app.cart.adapter.out.persistence


import com.ps.app.products.adapter.out.persistence.ProductEntity
import com.ps.app.user.adapter.out.persistence.UserEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "wishlist",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_wishlist_user_product",
            columnNames = ["user_id", "product_id"]
        )
    ],
    indexes = [
        Index(name = "idx_wishlist_user_id", columnList = "user_id")
    ]
)
class WishlistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: ProductEntity?
)
