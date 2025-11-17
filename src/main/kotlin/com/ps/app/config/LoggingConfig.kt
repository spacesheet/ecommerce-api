package com.ps.app.config

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

/**
 * 로깅 설정
 */
@Configuration
class LoggingConfig {

    /**
     * MDC (Mapped Diagnostic Context) 필터
     * 각 요청마다 고유한 추적 ID를 생성하여 로그에 포함
     */
    @Bean
    fun mdcFilter(): MdcFilter {
        return MdcFilter()
    }

    /**
     * 프로그래밍 방식으로 Appender 설정 (선택사항)
     * 일반적으로 logback-spring.xml을 사용하지만,
     * 동적 설정이 필요한 경우 이렇게 구성 가능
     */
    @Bean
    fun configureLogback(): LoggerContext {
        val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext

        // Console Appender 설정
        val consoleAppender = createConsoleAppender(loggerContext)

        // File Appender 설정 (선택사항)
        val fileAppender = createRollingFileAppender(loggerContext)

        // Root Logger 설정
        val rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME)
        rootLogger.level = Level.INFO
        rootLogger.addAppender(consoleAppender)
        rootLogger.addAppender(fileAppender)

        // 특정 패키지 로거 설정
        configurePackageLoggers(loggerContext)

        return loggerContext
    }

    /**
     * Console Appender 생성
     */
    private fun createConsoleAppender(context: LoggerContext): ConsoleAppender<ILoggingEvent> {
        val encoder = PatternLayoutEncoder().apply {
            this.context = context
            pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%X{requestId}] %-5level %logger{36} - %msg%n"
            start()
        }

        return ConsoleAppender<ILoggingEvent>().apply {
            this.context = context
            this.encoder = encoder
            name = "CONSOLE"
            start()
        }
    }

    /**
     * Rolling File Appender 생성
     */
    private fun createRollingFileAppender(context: LoggerContext): RollingFileAppender<ILoggingEvent> {
        val encoder = PatternLayoutEncoder().apply {
            this.context = context
            pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%X{requestId}] %-5level %logger{36} - %msg%n"
            start()
        }

        val rollingPolicy = TimeBasedRollingPolicy<ILoggingEvent>().apply {
            this.context = context
            fileNamePattern = "logs/app-%d{yyyy-MM-dd}.log"
            maxHistory = 30 // 30일 보관
            setParent(RollingFileAppender<ILoggingEvent>())
            start()
        }

        return RollingFileAppender<ILoggingEvent>().apply {
            this.context = context
            this.encoder = encoder
            file = "logs/app.log"
            this.rollingPolicy = rollingPolicy
            rollingPolicy.setParent(this)
            name = "FILE"
            start()
        }
    }

    /**
     * 패키지별 로그 레벨 설정
     */
    private fun configurePackageLoggers(context: LoggerContext) {
        // Domain 계층 - INFO 레벨
        context.getLogger("com.example.demo.domain").level = Level.INFO

        // Application 계층 - DEBUG 레벨
        context.getLogger("com.example.demo.application").level = Level.DEBUG

        // Infrastructure 계층
        context.getLogger("com.example.demo.infrastructure.adapter.in").level = Level.INFO
        context.getLogger("com.example.demo.infrastructure.adapter.out").level = Level.DEBUG
        context.getLogger("com.example.demo.infrastructure.aspect").level = Level.DEBUG

        // Hibernate SQL 로그
        context.getLogger("org.hibernate.SQL").level = Level.DEBUG
        context.getLogger("org.hibernate.type.descriptor.sql.BasicBinder").level = Level.TRACE
    }
}

/**
 * MDC 필터
 * 모든 HTTP 요청에 고유한 requestId를 부여
 */
class MdcFilter : OncePerRequestFilter() {

    companion object {
        const val REQUEST_ID_KEY = "requestId"
        const val USER_ID_KEY = "userId"
        const val REQUEST_URI_KEY = "requestUri"
        const val REQUEST_METHOD_KEY = "requestMethod"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            // 고유한 요청 ID 생성
            val requestId = generateRequestId()
            MDC.put(REQUEST_ID_KEY, requestId)

            // 요청 정보 MDC에 추가
            MDC.put(REQUEST_URI_KEY, request.requestURI)
            MDC.put(REQUEST_METHOD_KEY, request.method)

            // 사용자 ID (인증 정보가 있다면)
            // val userId = SecurityContextHolder.getContext().authentication?.name
            // MDC.put(USER_ID_KEY, userId ?: "anonymous")

            // 다음 필터로 진행
            filterChain.doFilter(request, response)
        } finally {
            // 요청 처리 완료 후 MDC 정리
            MDC.clear()
        }
    }

    /**
     * 요청 ID 생성
     */
    private fun generateRequestId(): String {
        return UUID.randomUUID().toString().substring(0, 8)
    }
}

/**
 * MDC 유틸리티
 * 
 * 사용 예시:
 * ```kotlin
 * MdcUtils.putUserId("user123")
 * try {
 *     // 비즈니스 로직
 * } finally {
 *     MdcUtils.removeUserId()
 * }
 * ```
 */
object MdcUtils {

    /**
     * 사용자 ID를 MDC에 추가
     */
    fun putUserId(userId: String) {
        MDC.put("userId", userId)
    }

    /**
     * 사용자 ID를 MDC에서 제거
     */
    fun removeUserId() {
        MDC.remove("userId")
    }

    /**
     * 커스텀 키-값을 MDC에 추가
     */
    fun put(key: String, value: String) {
        MDC.put(key, value)
    }

    /**
     * MDC에서 값 제거
     */
    fun remove(key: String) {
        MDC.remove(key)
    }

    /**
     * MDC 전체 정리
     */
    fun clear() {
        MDC.clear()
    }

    /**
     * 특정 컨텍스트에서 실행
     */
    inline fun <T> withContext(key: String, value: String, block: () -> T): T {
        return try {
            put(key, value)
            block()
        } finally {
            remove(key)
        }
    }
}
