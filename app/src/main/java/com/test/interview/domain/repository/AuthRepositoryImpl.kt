package com.test.interview.domain.repository

import com.test.interview.core.network.ApiService
import com.test.interview.data.model.LoginRequest
import com.test.interview.data.model.SignupRequest
import com.test.interview.domain.model.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Result<User> {
        return try {
            val response = apiService.login(
                LoginRequest(
                    email = email,
                    password = password
                )
            )

            val userDto = response.user
                ?: return Result.failure(
                    Exception("User data is missing")
                )

            Result.success(
                User(
                    id = userDto.id,
                    name = userDto.name,
                    email = userDto.email
                )
            )
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun signup(
        name: String,
        email: String,
        password: String
    ): Result<User> {
        return try {
            val response = apiService.signup(
                SignupRequest(
                    name = name,
                    email = email,
                    password = password
                )
            )

            val userDto = response.user
                ?: return Result.failure(
                    Exception("User data is missing")
                )

            Result.success(
                User(
                    id = userDto.id,
                    name = userDto.name,
                    email = userDto.email
                )
            )
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}