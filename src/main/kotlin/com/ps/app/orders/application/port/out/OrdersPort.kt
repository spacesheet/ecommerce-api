package com.ps.app.orders.application.port.out

import com.ps.app.orders.domain.Orders

interface OrdersPort {
    fun save(order: Orders): Orders
    fun findById(id: Long): Orders?
    fun findByOrderStr(orderStr: String): Orders?
    fun findByUserId(userId: Long): List<Orders>
    fun findAll(): List<Orders>
    fun delete(id: Long)
    fun existsByOrderStr(orderStr: String): Boolean
}
