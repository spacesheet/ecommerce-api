package com.ps.app.user

import com.ps.app.user.domain.User
import com.ps.app.user.domain.constant.UserStatus
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

class UserTest {
    @Test
    fun `사용자 비활성화 테스트`() {
        // given
        val user = User.create("space", "010-0000-0000", "name", "email@email.com", "12345678", LocalDate.parse("2025-01-01"), true);

        // when
        user.deactivate()

        // then
        assertEquals(UserStatus.WITHDRAW, user.status)
    }
}
