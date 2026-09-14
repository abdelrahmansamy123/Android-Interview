package com.test.interview.data.model

import com.test.interview.domain.model.User

data class AuthResponse(
    val token: String?,
    val user: User?
)