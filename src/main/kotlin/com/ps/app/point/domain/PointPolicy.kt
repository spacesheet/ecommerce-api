package com.ps.app.point.domain

data class PointPolicy(
    val id: Long = 0,
    val name: String,
    val point: Int = 0,
    val rate: Double = 1.0,
    val deleted: Boolean = false
) {
    init {
        require(name.isNotBlank()) { "Name cannot be blank" }
        require(rate >= 0) { "Rate must be non-negative" }
    }

    fun changePoint(newPoint: Int): PointPolicy {
        return copy(point = newPoint)
    }

    fun changeRate(newRate: Double): PointPolicy {
        require(newRate >= 0) { "Rate must be non-negative" }
        return copy(rate = newRate)
    }

    fun delete(): PointPolicy {
        return copy(deleted = true)
    }

    fun isActive(): Boolean = !deleted

    companion object {
        fun create(
            name: String,
            point: Int = 0,
            rate: Double = 1.0
        ): PointPolicy {
            return PointPolicy(
                name = name,
                point = point,
                rate = rate,
                deleted = false
            )
        }
    }
}
