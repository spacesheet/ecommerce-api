package com.ps.app.aspect

/**
 * 메서드별 성능 통계 데이터 클래스
 */
data class MethodPerformanceStats(
    val totalCalls: Long,
    val totalTime: Long,
    val minTime: Long,
    val maxTime: Long,
    val errorCount: Long
) {
    val averageTime: Long
        get() = if (totalCalls > 0) totalTime / totalCalls else 0

    val errorRate: Double
        get() = if (totalCalls > 0) (errorCount.toDouble() / totalCalls) * 100 else 0.0
}
