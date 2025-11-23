package com.ps.app.product.adapter.out.persistence

import com.ps.app.product.application.port.out.PublisherPort
import com.ps.app.product.domain.Publisher
import org.springframework.stereotype.Repository

@Repository
class PublisherPersistenceAdapter(
    private val publisherJpaRepository: PublisherJpaRepository
) : PublisherPort {

    override fun save(publisher: Publisher): Publisher {
        val entity = PublisherEntity.fromDomain(publisher)
        val saved = publisherJpaRepository.save(entity)
        return saved.toDomain()
    }

    override fun findById(id: Int): Publisher? {
        return publisherJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findByName(name: String): Publisher? {
        return publisherJpaRepository.findByName(name)?.toDomain()
    }

    override fun findAll(): List<Publisher> {
        return publisherJpaRepository.findAll().map { it.toDomain() }
    }

    override fun delete(publisher: Publisher) {
        publisherJpaRepository.deleteById(publisher.id)
    }

    override fun existsById(id: Int): Boolean {
        return publisherJpaRepository.existsById(id)
    }
}
