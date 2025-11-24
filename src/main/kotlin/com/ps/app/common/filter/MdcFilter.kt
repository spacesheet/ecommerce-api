package com.ps.app.common.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID


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
