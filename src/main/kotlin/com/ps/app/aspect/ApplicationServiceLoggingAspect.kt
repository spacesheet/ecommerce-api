package com.ps.app.aspect

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

/**
 * 2. Application Service 로깅 (Use Case 실행)
 */
@Aspect
@Component
@Order(2)
class ApplicationServiceLoggingAspect {

    private val logger = KotlinLogging.logger {}

    @Around("execution(* com.ps.app.*.application.service.in..*(..))")
    fun logApplicationService(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val methodName = signature.method.name
        val className = joinPoint.target.javaClass.simpleName
        val args = joinPoint.args

        logger.info { "🟢 [USE-CASE] $className.$methodName 실행 시작 - 파라미터: ${args.contentToString()}" }

        var result: Any? = null
        val executionTime = measureTimeMillis {
            try {
                result = joinPoint.proceed()
            } catch (e: Exception) {
                logger.error(e) { "🔴 [USE-CASE] $className.$methodName 실행 실패" }
                throw e
            }
        }

        logger.info { "🟢 [USE-CASE] $className.$methodName 실행 완료 (${executionTime}ms)" }
        return result
    }
}
