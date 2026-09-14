package com.test.interview.domain.usecase

import com.test.interview.domain.model.User
import com.test.interview.domain.repository.AuthRepository
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ): Result<User> {
        return repository.signup(
            name = name,
            email = email,
            password = password
        )
    }
}