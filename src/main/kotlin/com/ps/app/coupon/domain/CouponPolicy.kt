package com.ps.app.coupon.domain

import com.ps.app.coupon.domain.constant.DiscountType
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDate

/**
 * 쿠폰 정책 엔티티 클래스입니다.
 *
 * 이 클래스는 쿠폰 정책의 ID, 쿠폰 타입, 이름, 할인 타입, 할인율, 할인 금액, 기간, 기준 가격, 최대 할인 금액, 시작일, 종료일, 삭제 여부를 포함합니다.
 */
@Entity
class CouponPolicy(
    /**
     * 쿠폰 정책의 ID 입니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    /**
     * 쿠폰 타입을 나타내는 CouponType 객체입니다.
     *
     * 쿠폰 타입은 LAZY 로딩 전략을 사용하여 필요할 때 로드됩니다.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    var couponType: CouponType,

    /**
     * 쿠폰 정책의 이름입니다.
     */
    @NotNull
    @Column(length = 30)
    var name: String,

    /**
     * 쿠폰 정책의 할인 타입입니다.
     */
    @NotNull
    @Enumerated(value = EnumType.STRING)
    var discountType: DiscountType,

    /**
     * 쿠폰 정책의 할인율입니다.
     */
    @NotNull
    @ColumnDefault("0")
    var discountRate: Double,

    /**
     * 쿠폰 정책의 할인 금액입니다.
     */
    @NotNull
    @ColumnDefault("0")
    var discountAmount: Int,

    /**
     * 쿠폰 정책의 지속 기간입니다.
     */
    @NotNull
    var period: Int,

    /**
     * 쿠폰 정책의 기준 가격입니다.
     */
    @NotNull
    var standardPrice: Int,

    /**
     * 쿠폰 정책의 최대 할인 금액입니다.
     */
    @NotNull
    var maxDiscountAmount: Int,

    /**
     * 쿠폰 정책의 시작일입니다.
     */
    @NotNull
    var startDate: LocalDate,

    /**
     * 쿠폰 정책의 종료일입니다.
     */
    @NotNull
    var endDate: LocalDate,

    /**
     * 쿠폰 정책의 삭제 여부입니다.
     */
    @NotNull
    var deleted: Boolean
) {
    /**
     * 쿠폰 정책의 종료일을 변경합니다.
     *
     * @param endDate 새로운 종료일
     */
    fun changeEndDate(endDate: LocalDate) {
        this.endDate = endDate
    }

    /**
     * 쿠폰 정책을 삭제 상태로 변경합니다.
     */
    fun delete() {
        this.deleted = true
    }
}
