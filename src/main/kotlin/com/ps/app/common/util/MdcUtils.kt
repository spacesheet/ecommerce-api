package com.ps.app.common.util

import org.slf4j.MDC


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
