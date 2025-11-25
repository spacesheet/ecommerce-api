package com.ps.app.products.adapter.out.persistence

import com.fasterxml.jackson.annotation.JsonProperty
import com.ps.app.products.domain.Book
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "book")
class BookEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val title: String,

    @Column(columnDefinition = "text")
    val description: String?,

    @Column(length = 13, nullable = false)
    val isbn: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false)
    val publisher: PublisherEntity,

    @Column(nullable = false)
    @JsonProperty("pubdate")
    val publishDate: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    var product: ProductEntity? = null,

    @OneToMany(mappedBy = "book", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val bookAuthors: MutableList<BookAuthorEntity> = mutableListOf()
) {
    fun toDomain(): Book = Book(
        id = id,
        title = title,
        description = description,
        isbn = isbn,
        publisher = publisher.toDomain(),
        publishDate = publishDate,
        product = product?.toDomain()
    )

    companion object {
        fun fromDomain(
            domain: Book,
            publisherEntity: PublisherEntity,
            productEntity: ProductEntity?
        ): BookEntity {
            return BookEntity(
                id = domain.id,
                title = domain.title,
                description = domain.description,
                isbn = domain.isbn,
                publisher = publisherEntity,
                publishDate = domain.publishDate,
                product = productEntity
            )
        }
    }
}
