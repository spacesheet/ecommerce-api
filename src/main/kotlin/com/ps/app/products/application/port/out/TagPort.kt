package com.ps.app.products.application.port.out

import com.ps.app.products.domain.Tag

interface TagPort {
    fun save(tag: Tag): Tag
    fun findById(id: Int): Tag?
    fun findByName(name: String): Tag?
    fun findAll(): List<Tag>
    fun delete(tag: Tag)
}
