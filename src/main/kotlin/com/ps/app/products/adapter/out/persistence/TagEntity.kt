package com.ps.app.products.adapter.out.persistence

import com.ps.app.products.domain.Tag
import jakarta.persistence.*

@Entity
@Table(name = "tag")
class TagEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false, length = 20)
    val name: String
) {
    fun toDomain(): Tag = Tag(id = id, name = name)

    companion object {
        fun fromDomain(domain: Tag): TagEntity {
            return TagEntity(id = domain.id, name = domain.name)
        }
    }
}
