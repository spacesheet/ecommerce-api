package com.ps.app.user.adapter.out.persistence

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "grade")
class GradeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    val name: String,

    @NotNull
    @Column(nullable = false)
    val standard: Int,

    @NotNull
    @Column(nullable = false)
    val benefit: Double
) {
    protected constructor() : this(0, "", 0, 0.0)
}

