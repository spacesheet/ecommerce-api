package com.ps.app.aspect

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis


@Aspect
@Component
class LoggingAspect {

    private val logger = KotlinLogging.logger {}


    /**
     * Controller 레이어 포인트컷
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    fun restControllerPointcut() {}

    /**
     * Service 레이어 포인트컷
     */
    @Pointcut("within(@org.springframework.stereotype.Service *)")
    fun servicePointcut() {}

    /**
     * Repository 레이어 포인트컷
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    fun repositoryPointcut() {}

    /**
     * 커스텀 애노테이션 기반 포인트컷
     */
    @Pointcut("@annotation(com.example.demo.annotation.Loggable)")
    fun loggableAnnotationPointcut() {}

    /**
     * Controller, Service, Repository 메서드 실행 로깅
     */
    @Around("restControllerPointcut() || servicePointcut() || repositoryPointcut()")
    fun logAround(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val methodName = signature.method.name
        val className = joinPoint.target.javaClass.simpleName
        val args = joinPoint.args

        logger.info { "[$className.$methodName] 시작 - 파라미터: ${args.contentToString()}" }

        var result: Any? = null
        val executionTime = measureTimeMillis {
            try {
                result = joinPoint.proceed()
            } catch (e: Exception) {
                logger.error(e) { "[$className.$methodName] 예외 발생" }
                throw e
            }
        }

        logger.info { "[$className.$methodName] 종료 - 실행시간: ${executionTime}ms, 결과: $result" }
        
        return result
    }

    /**
     * @Loggable 애노테이션이 붙은 메서드만 로깅
     */
    @Around("loggableAnnotationPointcut()")
    fun logCustomAnnotation(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val methodName = signature.method.name
        val className = joinPoint.target.javaClass.simpleName

        logger.debug { "[@Loggable] $className.$methodName 호출" }
        
        return try {
            joinPoint.proceed()
        } catch (e: Exception) {
            logger.error(e) { "[@Loggable] $className.$methodName 실행 중 오류" }
            throw e
        }
    }
}
