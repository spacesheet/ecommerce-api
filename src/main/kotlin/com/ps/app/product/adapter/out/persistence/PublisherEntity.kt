package com.ps.app.product.adapter.out.persistence

import com.ps.app.product.domain.Publisher
import jakarta.persistence.*

@Entity
@Table(name = "publisher")
class PublisherEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false)
    val name: String,

    @OneToMany(mappedBy = "publisher", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val books: MutableList<BookEntity> = mutableListOf()
) {
    fun toDomain(): Publisher = Publisher(
        id = id,
        name = name
    )

    companion object {
        fun fromDomain(domain: Publisher): PublisherEntity {
            return PublisherEntity(
                id = domain.id,
                name = domain.name
            )
        }
    }
}
