package com.ps.app.point.application.service

import com.ps.app.point.application.port.`in`.CreatePointLogCommand
import com.ps.app.point.application.port.out.PointLogPort
import com.ps.app.point.application.usecases.PointLogUseCase
import com.ps.app.point.domain.PointLog
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PointLogService(
    private val pointLogPort: PointLogPort
) : PointLogUseCase {

    @Transactional
    override fun createPointLog(command: CreatePointLogCommand): PointLog {
        val pointLog = PointLog.Companion.create(
            userId = command.userId,
            inquiry = command.inquiry,
            delta = command.delta,
            balance = command.balance
        )
        return pointLogPort.save(pointLog)
    }

    override fun getPointLogsByUserId(userId: Long): List<PointLog> {
        return pointLogPort.findAllByUserIdOrderByCreatedAtDesc(userId)
    }
}
