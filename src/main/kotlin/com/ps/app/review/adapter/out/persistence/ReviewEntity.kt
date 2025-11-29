package com.ps.app.review.adapter.out.persistence

import com.ps.app.orders.adapter.out.persistence.OrderDetailEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "review",
    indexes = [
        Index(name = "idx_review_order_detail_id", columnList = "order_detail_id"),
        Index(name = "idx_review_created_at", columnList = "review_created_at")
    ]
)
class ReviewEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false, length = 1000)
    var content: String,

    @Column(columnDefinition = "TEXT")
    var picturePath: String? = null,

    @Column(nullable = false)
    var reviewScore: Int,

    @Column(nullable = false)
    val reviewCreatedAt: LocalDateTime = LocalDateTime.now(),

    // ⭐ OrderDetail 연관관계 - 이 필드가 있어야 함
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_id", unique = true, nullable = false)
    val orderDetail: OrderDetailEntity
) {
    fun updateContent(newContent: String) {
        this.content = newContent
    }

    fun updateScore(newScore: Int) {
        this.reviewScore = newScore
    }

    fun updatePicturePath(newPath: String?) {
        this.picturePath = newPath
    }
}
