package com.ps.app.point.adapter.out.persistence


import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "point_log")
class PointLogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, name = "user_id")
    val userId: Long,

    @Column(nullable = false, name = "create_at")
    val createAt: LocalDateTime,

    @Column(nullable = false)
    val inquiry: String,

    @Column(nullable = false)
    val delta: Int,

    @Column(nullable = false)
    val balance: Int
)
