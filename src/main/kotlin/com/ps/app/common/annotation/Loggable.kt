package com.ps.app.common.annotation

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Loggable(
    val level: LogLevel = LogLevel.INFO,
    val includeArgs: Boolean = true,           // ⭐ 이것 필요!
    val includeResult: Boolean = true,         // ⭐ 이것 필요!
    val includeExecutionTime: Boolean = true   // ⭐ 이것 필요!
)

enum class LogLevel {
    DEBUG, INFO, WARN, ERROR
}
