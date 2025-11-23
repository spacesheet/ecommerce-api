package com.ps.app.product.application.port.out

import com.ps.app.product.domain.Tag

interface TagPort {
    fun save(tag: Tag): Tag
    fun findById(id: Int): Tag?
    fun findByName(name: String): Tag?
    fun findAll(): List<Tag>
    fun delete(tag: Tag)
}
