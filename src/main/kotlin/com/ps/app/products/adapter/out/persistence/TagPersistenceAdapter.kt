package com.ps.app.products.adapter.out.persistence

import com.ps.app.products.application.port.out.TagPort
import com.ps.app.products.domain.Tag
import org.springframework.stereotype.Repository

@Repository
class TagPersistenceAdapter(
    private val tagJpaRepository: TagJpaRepository
) : TagPort {

    override fun save(tag: Tag): Tag {
        val entity = TagEntity.fromDomain(tag)
        val saved = tagJpaRepository.save(entity)
        return saved.toDomain()
    }

    override fun findById(id: Int): Tag? {
        return tagJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findByName(name: String): Tag? {
        return tagJpaRepository.findByName(name)?.toDomain()
    }

    override fun findAll(): List<Tag> {
        return tagJpaRepository.findAll().map { it.toDomain() }
    }

    override fun delete(tag: Tag) {
        tagJpaRepository.deleteById(tag.id)
    }
}
