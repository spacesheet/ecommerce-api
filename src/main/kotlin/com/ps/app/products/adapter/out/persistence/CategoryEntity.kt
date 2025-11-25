package com.ps.app.products.adapter.out.persistence

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.ps.app.products.domain.Category
import jakarta.persistence.*
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
@Table(name = "category")
class CategoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false, length = 20)
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonManagedReference
    val parentCategory: CategoryEntity? = null,

    @JsonBackReference
    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    val subCategories: MutableList<CategoryEntity> = mutableListOf()
) {
    fun toDomain(): Category {
        return Category(
            id = id,
            name = name,
            parentCategory = parentCategory?.toDomain(),
            subCategories = subCategories.map { it.toDomain() }
        )
    }

    /**
     * 하위 카테고리를 로드하지 않고 경량 도메인 객체로 변환
     */
    fun toDomainWithoutSubCategories(): Category {
        return Category(
            id = id,
            name = name,
            parentCategory = parentCategory?.toDomainWithoutSubCategories(),
            subCategories = emptyList()
        )
    }

    companion object {
        fun fromDomain(domain: Category, parentEntity: CategoryEntity? = null): CategoryEntity {
            return CategoryEntity(
                id = domain.id,
                name = domain.name,
                parentCategory = parentEntity
            )
        }
    }
}
