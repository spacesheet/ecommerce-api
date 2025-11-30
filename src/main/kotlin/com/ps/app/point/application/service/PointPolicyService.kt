package com.ps.app.point.application.service


import com.ps.app.point.exception.PointPolicyNotFoundException
import com.ps.app.point.application.port.`in`.CreatePointPolicyCommand
import com.ps.app.point.application.port.`in`.UpdatePointPolicyPointCommand
import com.ps.app.point.application.port.`in`.UpdatePointPolicyRateCommand
import com.ps.app.point.application.port.out.PointPolicyPort
import com.ps.app.point.application.usecases.PointPolicyUseCase
import com.ps.app.point.domain.PointPolicy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PointPolicyService(
    private val pointPolicyPort: PointPolicyPort
) : PointPolicyUseCase {

    @Transactional
    override fun createPointPolicy(command: CreatePointPolicyCommand): PointPolicy {
        val pointPolicy = PointPolicy.create(
            name = command.name,
            point = command.point,
            rate = command.rate
        )
        return pointPolicyPort.save(pointPolicy)
    }

    @Transactional
    override fun updatePointPolicyPoint(command: UpdatePointPolicyPointCommand): PointPolicy {
        val pointPolicy = getPointPolicyOrThrow(command.id)
        val updatedPolicy = pointPolicy.changePoint(command.point)
        return pointPolicyPort.save(updatedPolicy)
    }

    @Transactional
    override fun updatePointPolicyRate(command: UpdatePointPolicyRateCommand): PointPolicy {
        val pointPolicy = getPointPolicyOrThrow(command.id)
        val updatedPolicy = pointPolicy.changeRate(command.rate)
        return pointPolicyPort.save(updatedPolicy)
    }

    @Transactional
    override fun deletePointPolicy(id: Long): PointPolicy {
        val pointPolicy = getPointPolicyOrThrow(id)
        val deletedPolicy = pointPolicy.delete()
        return pointPolicyPort.save(deletedPolicy)
    }

    override fun getPointPolicy(id: Long): PointPolicy {
        return getPointPolicyOrThrow(id)
    }

    override fun getAllActivePolicies(): List<PointPolicy> {
        return pointPolicyPort.findAllActive()
    }

    private fun getPointPolicyOrThrow(id: Long): PointPolicy {
        return pointPolicyPort.findById(id)
            ?: throw PointPolicyNotFoundException("PointPolicy not found with id: $id")
    }
}
