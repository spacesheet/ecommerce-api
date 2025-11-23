package com.ps.app.product.adapter.out.persistence

import com.fasterxml.jackson.annotation.JsonBackReference
import com.ps.app.product.adapter.out.persistence.pk.BookAuthorPK
import com.ps.app.product.domain.BookAuthor
import jakarta.persistence.*

@Entity
@Table(
    name = "book_author",
    uniqueConstraints = [UniqueConstraint(columnNames = ["author_id", "book_id"])]
)
class BookAuthorEntity(
    @EmbeddedId
    val pk: BookAuthorPK,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @MapsId("authorId")
    @JsonBackReference
    val author: AuthorEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @MapsId("bookId")
    val book: BookEntity
) {
    fun toDomain(): BookAuthor = BookAuthor(
        bookId = pk.bookId,
        authorId = pk.authorId,
        author = author.toDomain(),
        book = book.toDomain()
    )

    companion object {
        fun fromDomain(
            domain: BookAuthor,
            authorEntity: AuthorEntity,
            bookEntity: BookEntity
        ): BookAuthorEntity {
            return BookAuthorEntity(
                pk = BookAuthorPK(domain.bookId, domain.authorId),
                author = authorEntity,
                book = bookEntity
            )
        }
    }
}
