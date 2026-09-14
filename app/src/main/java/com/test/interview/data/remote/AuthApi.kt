package com.test.interview.data.remote

import com.test.interview.data.model.AuthResponse
import com.test.interview.data.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse
}