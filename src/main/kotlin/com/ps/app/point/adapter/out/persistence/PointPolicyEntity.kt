package com.ps.app.point.adapter.out.persistence


import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "point_policy")
class PointPolicyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val point: Int = 0,

    @Column(nullable = false)
    @ColumnDefault("1.0")
    val rate: Double = 1.0,

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    val deleted: Boolean = false
)
