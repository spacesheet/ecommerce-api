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
