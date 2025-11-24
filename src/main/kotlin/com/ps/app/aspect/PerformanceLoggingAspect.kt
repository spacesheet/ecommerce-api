package com.ps.app.aspect

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureTimeMillis


/**
 * 성능 모니터링 Aspect
 * - 메서드 실행 시간 측정
 * - 느린 메서드 자동 감지 및 경고
 * - 성능 통계 수집
 */
@Aspect
@Component
@Order(5)
class PerformanceLoggingAspect {

    private val logger = KotlinLogging.logger {}


    companion object {
        // 계층별 임계값 (milliseconds)
        private const val CONTROLLER_THRESHOLD = 3000L      // 3초
        private const val SERVICE_THRESHOLD = 2000L         // 2초
        private const val REPOSITORY_THRESHOLD = 1000L      // 1초
        
        private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    }

    // 메서드별 성능 통계
    private val performanceStats = ConcurrentHashMap<String, MethodPerformanceStats>()

    /**
     * 모든 비즈니스 로직 계층의 성능 모니터링
     */
    @Around("execution(* com.example.demo.application..*(..)) || " +
            "execution(* com.example.demo.infrastructure.adapter.in..*(..)) || " +
            "execution(* com.example.demo.infrastructure.adapter.out..*(..))")
    fun monitorPerformance(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val methodName = signature.method.name
        val className = joinPoint.target.javaClass.simpleName
        val methodKey = "$className.$methodName"
        val packageName = joinPoint.target.javaClass.packageName

        var result: Any? = null
        var executionTime = 0L
        var isError = false

        try {
            executionTime = measureTimeMillis {
                result = joinPoint.proceed()
            }
        } catch (e: Exception) {
            isError = true
            throw e
        } finally {
            // 통계 업데이트
            updateStats(methodKey, executionTime, isError)

            // 임계값 초과 체크
            val threshold = getThreshold(packageName)
            if (executionTime > threshold) {
                logSlowMethod(methodKey, executionTime, threshold, packageName)
            }

            // 주기적으로 통계 출력 (100번째 호출마다)
            val stats = performanceStats[methodKey]
            if (stats != null && stats.totalCalls % 100L == 0L) {
                logStatistics(methodKey, stats)
            }
        }

        return result
    }

    /**
     * 계층별 임계값 결정
     */
    private fun getThreshold(packageName: String): Long {
        return when {
            packageName.contains(".adapter.in") -> CONTROLLER_THRESHOLD
            packageName.contains(".application") -> SERVICE_THRESHOLD
            packageName.contains(".adapter.out") -> REPOSITORY_THRESHOLD
            else -> SERVICE_THRESHOLD
        }
    }

    /**
     * 느린 메서드 로깅
     */
    private fun logSlowMethod(methodKey: String, executionTime: Long, threshold: Long, packageName: String) {
        val layer = determineLayer(packageName)
        val timestamp = LocalDateTime.now().format(dateFormatter)
        
        logger.warn {
            """
            |╔═══════════════════════════════════════════════════════════════
            |║ ⚠️ SLOW METHOD DETECTED
            |╠═══════════════════════════════════════════════════════════════
            |║ 시간: $timestamp
            |║ 계층: $layer
            |║ 메서드: $methodKey
            |║ 실행시간: ${executionTime}ms
            |║ 임계값: ${threshold}ms
            |║ 초과율: ${String.format("%.1f", (executionTime.toDouble() / threshold) * 100)}%
            |╚═══════════════════════════════════════════════════════════════
            """.trimMargin()
        }
    }

    /**
     * 성능 통계 로깅
     */
    private fun logStatistics(methodKey: String, stats: MethodPerformanceStats) {
        logger.info {
            """
            |📊 Performance Statistics for $methodKey:
            |  - Total Calls: ${stats.totalCalls}
            |  - Total Time: ${stats.totalTime}ms
            |  - Average Time: ${stats.averageTime}ms
            |  - Min Time: ${stats.minTime}ms
            |  - Max Time: ${stats.maxTime}ms
            |  - Error Count: ${stats.errorCount}
            |  - Error Rate: ${String.format("%.2f", stats.errorRate)}%
            """.trimMargin()
        }
    }

    /**
     * 통계 업데이트
     */
    private fun updateStats(methodKey: String, executionTime: Long, isError: Boolean) {
        performanceStats.compute(methodKey) { _, existing ->
            if (existing == null) {
                MethodPerformanceStats(
                    totalCalls = 1,
                    totalTime = executionTime,
                    minTime = executionTime,
                    maxTime = executionTime,
                    errorCount = if (isError) 1 else 0
                )
            } else {
                existing.copy(
                    totalCalls = existing.totalCalls + 1,
                    totalTime = existing.totalTime + executionTime,
                    minTime = minOf(existing.minTime, executionTime),
                    maxTime = maxOf(existing.maxTime, executionTime),
                    errorCount = if (isError) existing.errorCount + 1 else existing.errorCount
                )
            }
        }
    }

    /**
     * 계층 판별
     */
    private fun determineLayer(packageName: String): String {
        return when {
            packageName.contains(".adapter.in") -> "🔵 INBOUND-ADAPTER"
            packageName.contains(".application") -> "🔶 APPLICATION"
            packageName.contains(".adapter.out") -> "🟡 OUTBOUND-ADAPTER"
            else -> "❓ UNKNOWN"
        }
    }

    /**
     * 전체 통계 조회 (모니터링 엔드포인트용)
     */
    fun getAllStatistics(): Map<String, MethodPerformanceStats> {
        return performanceStats.toMap()
    }

    /**
     * 통계 초기화
     */
    fun resetStatistics() {
        performanceStats.clear()
        logger.info { "Performance statistics have been reset" }
    }
}

/**
 * 성능 모니터링 컨트롤러 (선택사항)
 * Actuator 엔드포인트처럼 성능 통계 조회 가능
 */
/*
@RestController
@RequestMapping("/actuator/performance")
class PerformanceMonitoringController(
    private val performanceAspect: PerformanceLoggingAspect
) {

    @GetMapping("/stats")
    fun getStatistics(): Map<String, MethodPerformanceStats> {
        return performanceAspect.getAllStatistics()
    }

    @PostMapping("/reset")
    fun resetStatistics(): Map<String, String> {
        performanceAspect.resetStatistics()
        return mapOf("message" to "Statistics have been reset")
    }
}
*/
