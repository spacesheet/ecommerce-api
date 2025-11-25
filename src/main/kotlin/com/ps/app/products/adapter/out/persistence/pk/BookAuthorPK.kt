package com.ps.app.products.adapter.out.persistence.pk

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class BookAuthorPK(
    @Column(name = "book_id")
    val bookId: Long = 0L,

    @Column(name = "author_id")
    val authorId: Int = 0
) : Serializable
