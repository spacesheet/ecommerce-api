package com.ps.app.products.adapter.out.persistence

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.ps.app.products.domain.Author
import jakarta.persistence.*

@Entity
@Table(name = "author")
class AuthorEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @OneToMany(mappedBy = "author", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JsonManagedReference
    val bookAuthors: MutableList<BookAuthorEntity> = mutableListOf()
) {
    fun toDomain(): Author = Author(
        id = id,
        name = name
    )

    companion object {
        fun fromDomain(domain: Author): AuthorEntity {
            return AuthorEntity(
                id = domain.id,
                name = domain.name
            )
        }
    }
}
