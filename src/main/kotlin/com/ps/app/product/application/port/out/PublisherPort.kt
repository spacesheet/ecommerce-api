package com.ps.app.product.application.port.out

import com.ps.app.product.domain.Publisher

interface PublisherPort {
    fun save(publisher: Publisher): Publisher
    fun findById(id: Int): Publisher?
    fun findByName(name: String): Publisher?
    fun findAll(): List<Publisher>
    fun delete(publisher: Publisher)
    fun existsById(id: Int): Boolean
}
