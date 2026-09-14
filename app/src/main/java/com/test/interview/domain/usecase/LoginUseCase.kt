package com.test.interview.domain.usecase

import com.test.interview.domain.model.User
import com.test.interview.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<User> {
        return repository.login(email, password)
    }
}