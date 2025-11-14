// ═══════════════════════════════════════════════════════════
// domain/vo/UserInfo.kt - 도메인 값 객체
// ═══════════════════════════════════════════════════════════
package com.ps.app.user.domain.vo

import com.ps.app.user.domain.Grade
import java.time.LocalDate

/**
 * 사용자 정보 도메인 값 객체
 * 순수한 도메인 개념을 표현하며, 기술 의존성이 없음
 */
data class UserInfo(
    val userId: Long?,
    val name: String,
    val loginId: String,
    val birthday: LocalDate,
    val isAdmin: Boolean,
    val grade: Grade,
    val contactNumber: String,
    val email: String,
    val point: Int
) {
    /**
     * 성인 여부 확인 (도메인 로직)
     */
    fun isAdult(): Boolean {
        return birthday.plusYears(18) <= LocalDate.now()
    }
}
