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

private val logger = KotlinLogging.logger {}

/**
 * 예외 로깅 Aspect
 * - 모든 계층에서 발생하는 예외를 포착하고 상세 로깅
 * - 가장 먼저 실행되어야 하므로 @Order(0)
 */
@Aspect
@Component
@Order(0)
class ExceptionLoggingAspect {

    companion object {
        private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    }

    /**
     * 전체 애플리케이션의 모든 메서드에 적용
     */
    @Around("execution(* com.ps.app..*(..))")
    fun logExceptions(joinPoint: ProceedingJoinPoint): Any? {
        return try {
            joinPoint.proceed()
        } catch (e: Exception) {
            logExceptionDetails(joinPoint, e)
            throw e
        }
    }

    /**
     * 예외 상세 정보 로깅
     */
    private fun logExceptionDetails(joinPoint: ProceedingJoinPoint, exception: Exception) {
        val signature = joinPoint.signature as MethodSignature
        val methodName = signature.method.name
        val className = joinPoint.target.javaClass.simpleName
        val packageName = joinPoint.target.javaClass.packageName
        val args = joinPoint.args
        val timestamp = LocalDateTime.now().format(dateFormatter)

        // 계층 판별
        val layer = determineLayer(packageName)

        // 예외 정보 구조화
        val exceptionInfo = buildString {
            appendLine("╔═══════════════════════════════════════════════════════════════")
            appendLine("║ ❌ 예외 발생")
            appendLine("╠═══════════════════════════════════════════════════════════════")
            appendLine("║ 시간: $timestamp")
            appendLine("║ 계층: $layer")
            appendLine("║ 클래스: $className")
            appendLine("║ 메서드: $methodName")
            appendLine("║ 파라미터: ${formatArgs(args)}")
            appendLine("╠═══════════════════════════════════════════════════════════════")
            appendLine("║ 예외 타입: ${exception.javaClass.simpleName}")
            appendLine("║ 예외 메시지: ${exception.message ?: "메시지 없음"}")
            
            // Cause 체인 추적
            var cause = exception.cause
            var depth = 1
            while (cause != null && depth <= 3) {
                appendLine("║ Caused by [$depth]: ${cause.javaClass.simpleName}: ${cause.message}")
                cause = cause.cause
                depth++
            }
            
            appendLine("╠═══════════════════════════════════════════════════════════════")
            appendLine("║ 스택 트레이스:")
            
            // 프로젝트 패키지의 스택만 표시
            exception.stackTrace
                .filter { it.className.startsWith("com.example.demo") }
                .take(10)
                .forEach { 
                    appendLine("║   at ${it.className}.${it.methodName}(${it.fileName}:${it.lineNumber})")
                }
            
            appendLine("╚═══════════════════════════════════════════════════════════════")
        }

        // 예외 타입에 따라 다른 로그 레벨 사용
        when {
            isBusinessException(exception) -> logger.warn { exceptionInfo }
            isValidationException(exception) -> logger.info { exceptionInfo }
            else -> logger.error { exceptionInfo }
        }
    }

    /**
     * 계층 판별
     */
    private fun determineLayer(packageName: String): String {
        return when {
            packageName.contains(".domain.") -> "🔷 DOMAIN"
            packageName.contains(".application.") -> "🔶 APPLICATION"
            packageName.contains(".infrastructure.adapter.in") -> "🔵 INBOUND-ADAPTER"
            packageName.contains(".infrastructure.adapter.out") -> "🟡 OUTBOUND-ADAPTER"
            packageName.contains(".infrastructure.") -> "🔸 INFRASTRUCTURE"
            else -> "❓ UNKNOWN"
        }
    }

    /**
     * 비즈니스 예외 판별 (경고 레벨)
     */
    private fun isBusinessException(exception: Exception): Boolean {
        val exceptionName = exception.javaClass.simpleName
        return exceptionName.contains("NotFound") ||
               exceptionName.contains("AlreadyExists") ||
               exceptionName.contains("Duplicate") ||
               exception.javaClass.packageName.contains(".domain.")
    }

    /**
     * 유효성 검사 예외 판별 (정보 레벨)
     */
    private fun isValidationException(exception: Exception): Boolean {
        return exception is IllegalArgumentException ||
               exception is IllegalStateException ||
               exception.javaClass.simpleName.contains("Validation")
    }

    /**
     * 파라미터 포맷팅 (민감 정보 마스킹)
     */
    private fun formatArgs(args: Array<Any?>): String {
        if (args.isEmpty()) return "없음"
        
        return args.joinToString(", ") { arg ->
            when (arg) {
                null -> "null"
                is String -> if (arg.length > 100) "\"${arg.take(100)}...\"" else "\"$arg\""
                is Number -> arg.toString()
                is Boolean -> arg.toString()
                is Collection<*> -> "[${arg.size} items]"
                is Map<*, *> -> "{${arg.size} entries}"
                else -> "${arg.javaClass.simpleName}@${System.identityHashCode(arg)}"
            }
        }
    }
}
