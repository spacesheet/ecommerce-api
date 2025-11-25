package com.ps.app.payment.adapter.out.persistence

import com.ps.app.payment.adapter.out.persistence.BillStatusEntity
import com.ps.app.payment.domain.BillStatus

object BillStatusMapper {
    fun toEntity(domain: BillStatus): BillStatusEntity {
        return when (domain) {
            BillStatus.BEFORE -> BillStatusEntity.BEFORE
            BillStatus.COMPLETED -> BillStatusEntity.COMPLETED
            BillStatus.CANCELED -> BillStatusEntity.CANCELED
            BillStatus.REFUND -> BillStatusEntity.REFUND
            BillStatus.DONE -> BillStatusEntity.DONE
            BillStatus.PARTIAL_CANCELED -> BillStatusEntity.PARTIAL_CANCELED
        }
    }

    fun toDomain(entity: BillStatusEntity): BillStatus {
        return when (entity) {
            BillStatusEntity.BEFORE -> BillStatus.BEFORE
            BillStatusEntity.COMPLETED -> BillStatus.COMPLETED
            BillStatusEntity.CANCELED -> BillStatus.CANCELED
            BillStatusEntity.REFUND -> BillStatus.REFUND
            BillStatusEntity.DONE -> BillStatus.DONE
            BillStatusEntity.PARTIAL_CANCELED -> BillStatus.PARTIAL_CANCELED
        }
    }
}
