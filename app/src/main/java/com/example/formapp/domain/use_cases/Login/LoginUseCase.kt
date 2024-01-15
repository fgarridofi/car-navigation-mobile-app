package com.example.formapp.domain.use_cases.Login

import com.example.formapp.data.Result
import com.example.formapp.domain.model.LoginModel2
import com.example.formapp.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor( private val repository: UserRepository) {

    suspend operator fun invoke( email: String, password: String): Result<Unit> {
        val loginModel2 = LoginModel2(email, password)
         return repository.authenticateUser(loginModel2)
    }
}