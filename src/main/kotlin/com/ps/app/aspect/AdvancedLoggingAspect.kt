package com.ps.app.aspect

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger {}

@Aspect
@Component
class AdvancedLoggingAspect {

    companion object {
        private const val SLOW_METHOD_THRESHOLD = 3000L // 3초
        private val SENSITIVE_FIELDS = setOf("password", "token", "secret", "apiKey")
    }

    /**
     * 실행 시간이 오래 걸리는 메서드 경고
     */
    @Around("execution(* com.ps.app.user.application.service..*(..))")
    fun logSlowMethods(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val methodName = signature.method.name
        val className = joinPoint.target.javaClass.simpleName

        var result: Any? = null
        val executionTime = measureTimeMillis {
            result = joinPoint.proceed()
        }

        if (executionTime > SLOW_METHOD_THRESHOLD) {
            logger.warn { 
                "⚠️ SLOW METHOD: [$className.$methodName] 실행시간: ${executionTime}ms (임계값: ${SLOW_METHOD_THRESHOLD}ms)" 
            }
        }

        return result
    }

    /**
     * 파라미터에서 민감한 정보 마스킹
     */
    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping)")
    fun logWithSensitiveDataMasking(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val methodName = signature.method.name
        val className = joinPoint.target.javaClass.simpleName
        val parameterNames = signature.parameterNames
        val args = joinPoint.args

        // 파라미터를 맵으로 변환하고 민감한 데이터 마스킹
        val maskedParams = parameterNames.zip(args).associate { (name, value) ->
            name to if (SENSITIVE_FIELDS.any { name.contains(it, ignoreCase = true) }) {
                "***MASKED***"
            } else {
                maskSensitiveFields(value)
            }
        }

        logger.info { "[$className.$methodName] 요청 파라미터: $maskedParams" }

        return try {
            val result = joinPoint.proceed()
            logger.info { "[$className.$methodName] 응답: ${maskSensitiveFields(result)}" }
            result
        } catch (e: Exception) {
            logger.error(e) { "[$className.$methodName] 오류 발생" }
            throw e
        }
    }

    /**
     * 객체 내의 민감한 필드를 마스킹
     */
    private fun maskSensitiveFields(obj: Any?): Any? {
        if (obj == null) return null
        
        return when (obj) {
            is String -> obj
            is Number -> obj
            is Boolean -> obj
            is Collection<*> -> "[${obj.size} items]"
            is Map<*, *> -> {
                obj.entries.associate { (key, value) ->
                    val keyStr = key.toString()
                    key to if (SENSITIVE_FIELDS.any { keyStr.contains(it, ignoreCase = true) }) {
                        "***MASKED***"
                    } else {
                        value
                    }
                }
            }
            else -> {
                // 복잡한 객체는 간단히 표현
                "${obj.javaClass.simpleName}@${System.identityHashCode(obj)}"
            }
        }
    }

    /**
     * 예외 발생 시 상세 로깅
     */
    @Around("execution(* com.ps.app..*(..))")
    fun logExceptionDetails(joinPoint: ProceedingJoinPoint): Any? {
        return try {
            joinPoint.proceed()
        } catch (e: Exception) {
            val signature = joinPoint.signature as MethodSignature
            val methodName = signature.method.name
            val className = joinPoint.target.javaClass.simpleName
            
            logger.error { 
                """
                |예외 발생 상세 정보:
                |  - 클래스: $className
                |  - 메서드: $methodName
                |  - 예외 타입: ${e.javaClass.simpleName}
                |  - 예외 메시지: ${e.message}
                |  - 스택 트레이스: ${e.stackTraceToString()}
                """.trimMargin()
            }
            throw e
        }
    }
}
