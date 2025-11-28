package com.ps.app.products.adapter.out.persistence

import com.ps.app.products.domain.Publisher
import com.ps.app.products.domain.PublisherId
import jakarta.persistence.*

@Entity
@Table(name = "publisher")
class PublisherEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @OneToMany(mappedBy = "publisher", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val books: MutableList<BookEntity> = mutableListOf()
) {
    fun toDomain(): Publisher = Publisher(
        id = PublisherId(id),  // Long -> PublisherId 변환
        name = name
    )

    companion object {
        fun fromDomain(domain: Publisher): PublisherEntity {
            return PublisherEntity(
                id = domain.id.value,  // PublisherId -> Long 변환
                name = domain.name
            )
        }
    }
}
