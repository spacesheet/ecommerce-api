package com.ps.app.coupons.adapter.out.persistence

import com.ps.app.coupons.domain.CouponType
import com.ps.app.coupons.domain.constant.CouponScope
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = false)
class CouponTypeConverter : AttributeConverter<CouponType, String> {

    override fun convertToDatabaseColumn(attribute: CouponType?): String? {
        return attribute?.name?.name
    }

    override fun convertToEntityAttribute(dbData: String?): CouponType? {
        return dbData?.let {
            val scope = CouponScope.valueOf(it)
            CouponType.create(scope)
        }
    }
}
