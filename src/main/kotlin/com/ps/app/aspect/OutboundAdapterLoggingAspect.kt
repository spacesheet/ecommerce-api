package com.ps.app.aspect

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger {}

/**
 * 3. Outbound Adapter 로깅 (Secondary Adapter - Repository)
 */
@Aspect
@Component
@Order(3)
class OutboundAdapterLoggingAspect {

    @Around("execution(* com.ps.app.*.adapter.out..*(..))")
    fun logOutboundAdapter(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val methodName = signature.method.name
        val className = joinPoint.target.javaClass.simpleName
        val args = joinPoint.args

        logger.debug { "🟡 [OUTBOUND] $className.$methodName - DB 접근 시작: ${args.contentToString()}" }

        var result: Any? = null
        val executionTime = measureTimeMillis {
            try {
                result = joinPoint.proceed()
            } catch (e: Exception) {
                logger.error(e) { "🔴 [OUTBOUND] $className.$methodName - DB 접근 오류" }
                throw e
            }
        }

        if (executionTime > 1000) {
            logger.warn { "⚠️ [OUTBOUND] SLOW QUERY: $className.$methodName (${executionTime}ms)" }
        } else {
            logger.debug { "🟡 [OUTBOUND] $className.$methodName - DB 접근 완료 (${executionTime}ms)" }
        }

        return result
    }
}
