package com.test.interview.domain.repository

import com.test.interview.domain.model.User

interface AuthRepository {

    suspend fun login(
        email: String,
        password: String
    ): Result<User>

    suspend fun signup(
        name: String,
        email: String,
        password: String
    ): Result<User>
}