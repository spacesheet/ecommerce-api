package com.ps.app.products.adapter.out.persistence

import com.fasterxml.jackson.annotation.JsonProperty
import com.ps.app.products.domain.Book
import com.ps.app.products.domain.BookId
import com.ps.app.products.domain.ISBN
import com.ps.app.products.domain.ProductId
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

    @Column(name = "product_id", nullable = true)
    val productId: Long? = null, // nullable!

    @OneToMany(mappedBy = "book", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val bookAuthors: MutableList<BookAuthorEntity> = mutableListOf()
) {
//    fun toDomain(): Book = Book(
//        id = BookId(id),
//        productId = productId?.let { ProductId(it) },
//        isbn = ISBN(isbn),
//        title = title,
//        author = author,
//        publisher = publisher.toDomain(),
//        publishDate = publishDate,
//        description = description,
//        coverImageUrl = coverImageUrl,
//        category = category
//    )
//
//    companion object {
//        fun fromDomain(
//            book: Book,
//            publisherEntity: PublisherEntity,
//            productEntity: ProductEntity?
//        ): BookEntity {
//            return BookEntity(
//                id = book.id.value,
//                productId = productEntity?.id,
//                isbn = book.isbn.value,
//                title = book.title,
//                author = book.author,
//                publisher = publisherEntity,
//                publishDate = book.publishDate,
//                description = book.description,
//                coverImageUrl = book.coverImageUrl,
//                category = book.category
//            )
//        }
//    }
}
