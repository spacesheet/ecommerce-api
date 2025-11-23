package com.ps.app.common.annotation

import com.ps.app.common.constant.LogLevel

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Loggable(
    val level: LogLevel = LogLevel.INFO,
    val includeArgs: Boolean = true,
    val includeResult: Boolean = true,
    val includeExecutionTime: Boolean = true
)
