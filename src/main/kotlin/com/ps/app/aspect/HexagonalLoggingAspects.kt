package com.ps.app.aspect

import com.ps.app.common.annotation.Loggable
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

/**
 * 2. Application Service 로깅 (Use Case 실행)
 */
@Aspect
@Component
@Order(2)
class ApplicationServiceLoggingAspect {

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

/**
 * 4. @Loggable 애노테이션 기반 로깅
 */
@Aspect
@Component
@Order(4)
class CustomLoggableAspect {

    @Around("@annotation(loggable)")
    fun logLoggableMethod(joinPoint: ProceedingJoinPoint, loggable: Loggable): Any? {
        val signature = joinPoint.signature as MethodSignature
        val methodName = signature.method.name
        val className = joinPoint.target.javaClass.simpleName

        if (loggable.includeArgs) {
            logger.info { "[@Loggable] $className.$methodName - Args: ${joinPoint.args.contentToString()}" }
        }

        var result: Any? = null
        val executionTime = measureTimeMillis {
            result = joinPoint.proceed()
        }

        if (loggable.includeExecutionTime) {
            logger.info { "[@Loggable] $className.$methodName - Time: ${executionTime}ms" }
        }

        if (loggable.includeResult) {
            logger.info { "[@Loggable] $className.$methodName - Result: $result" }
        }

        return result
    }
}

/**
 * 5. 예외 로깅 (모든 계층)
 */
@Aspect
@Component
@Order(0)  // 가장 먼저 실행
class ExceptionLoggingAspect {

    @Around("execution(* com.example.demo..*(..))")
    fun logExceptions(joinPoint: ProceedingJoinPoint): Any? {
        return try {
            joinPoint.proceed()
        } catch (e: Exception) {
            val signature = joinPoint.signature as MethodSignature
            val methodName = signature.method.name
            val className = joinPoint.target.javaClass.simpleName
            
            logger.error { 
                """
                |❌ 예외 발생:
                |  - 위치: $className.$methodName
                |  - 예외: ${e.javaClass.simpleName}
                |  - 메시지: ${e.message}
                """.trimMargin()
            }
            throw e
        }
    }
}
