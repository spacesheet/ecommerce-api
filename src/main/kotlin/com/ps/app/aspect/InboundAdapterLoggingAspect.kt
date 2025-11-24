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
 * 헥사고날 아키텍처 계층별 로깅 Aspect
 * Infrastructure 계층에 위치
 */

/**
 * 1. Inbound Adapter 로깅 (Primary Adapter - Controller)
 */
@Aspect
@Component
@Order(1)
class InboundAdapterLoggingAspect {

    @Around("execution(* com.ps.app.*.adapter.in..*(..))")
    fun logInboundAdapter(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val methodName = signature.method.name
        val className = joinPoint.target.javaClass.simpleName

        logger.info { "🔵 [INBOUND] $className.$methodName - 요청 시작" }
        
        var result: Any? = null
        val executionTime = measureTimeMillis {
            try {
                result = joinPoint.proceed()
            } catch (e: Exception) {
                logger.error(e) { "🔴 [INBOUND] $className.$methodName - 오류 발생" }
                throw e
            }
        }

        logger.info { "🔵 [INBOUND] $className.$methodName - 요청 완료 (${executionTime}ms)" }
        return result
    }
}
