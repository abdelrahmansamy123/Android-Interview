package com.test.interview.core.network

import com.test.interview.data.model.AuthResponse
import com.test.interview.data.model.LoginRequest
import com.test.interview.data.model.SignupRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse

    @POST("auth/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): AuthResponse
}