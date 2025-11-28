package com.ps.app.products.domain

import java.math.BigDecimal

@JvmInline
value class Money(val value: BigDecimal) {
    companion object {
        val ZERO = Money(BigDecimal.ZERO)

        fun of(amount: String): Money = Money(BigDecimal(amount))
        fun of(amount: Int): Money = Money(amount.toBigDecimal())
        fun of(amount: Long): Money = Money(amount.toBigDecimal())
    }

    operator fun plus(other: Money) = Money(value + other.value)
    operator fun minus(other: Money) = Money(value - other.value)
    operator fun times(multiplier: Int) = Money(value * multiplier.toBigDecimal())
    operator fun times(multiplier: Double) = Money(value * multiplier.toBigDecimal())

    fun isPositive() = value > BigDecimal.ZERO
    fun isZero() = value == BigDecimal.ZERO
    fun isNegative() = value < BigDecimal.ZERO
}
