package com.ps.app.point.application.usecases

import com.ps.app.point.application.port.`in`.CreatePointLogCommand
import com.ps.app.point.domain.PointLog

interface PointLogUseCase {
    fun createPointLog(command: CreatePointLogCommand): PointLog
    fun getPointLogsByUserId(userId: Long): List<PointLog>
}
