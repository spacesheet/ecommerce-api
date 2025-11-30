package com.ps.app.point.application.usecases

import com.ps.app.point.application.port.`in`.CreatePointPolicyCommand
import com.ps.app.point.application.port.`in`.UpdatePointPolicyPointCommand
import com.ps.app.point.application.port.`in`.UpdatePointPolicyRateCommand
import com.ps.app.point.domain.PointPolicy

interface PointPolicyUseCase {
    fun createPointPolicy(command: CreatePointPolicyCommand): PointPolicy
    fun updatePointPolicyPoint(command: UpdatePointPolicyPointCommand): PointPolicy
    fun updatePointPolicyRate(command: UpdatePointPolicyRateCommand): PointPolicy
    fun deletePointPolicy(id: Long): PointPolicy
    fun getPointPolicy(id: Long): PointPolicy
    fun getAllActivePolicies(): List<PointPolicy>
}
