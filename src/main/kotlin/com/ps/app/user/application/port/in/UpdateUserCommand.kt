package com.ps.app.user.application.port.`in`

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateUserCommand(
    @field:NotBlank
    @field:Size(max = 20)
    val name: String,

    @field:NotBlank
    @field:Size(min = 6, max = 15)
    val contactNumber: String,

    @field:NotBlank
    @field:Email
    val email: String
)
