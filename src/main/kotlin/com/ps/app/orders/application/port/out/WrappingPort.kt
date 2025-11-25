package com.ps.app.orders.application.port.out

import com.ps.app.orders.domain.Wrapping

interface WrappingPort {
    fun save(wrapping: Wrapping): Wrapping
    fun findById(id: Int): Wrapping?
    fun findAll(): List<Wrapping>
    fun findAllActive(): List<Wrapping>
}
