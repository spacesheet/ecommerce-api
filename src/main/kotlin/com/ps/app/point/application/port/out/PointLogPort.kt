package com.ps.app.point.application.port.out

import com.ps.app.point.domain.PointLog

interface PointLogPort {
    fun save(pointLog: PointLog): PointLog
    fun findById(id: Long): PointLog?
    fun findAllByUserId(userId: Long): List<PointLog>
    fun findAllByUserIdOrderByCreatedAtDesc(userId: Long): List<PointLog>
}
