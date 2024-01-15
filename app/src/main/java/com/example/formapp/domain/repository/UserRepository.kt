package com.example.formapp.domain.repository

import com.example.formapp.data.Result
import com.example.formapp.domain.model.LoginModel2
import com.example.formapp.domain.model.User

interface UserRepository {

    suspend fun insertUser(user: User): Result<Unit>

    suspend fun authenticateUser(loginModel2: LoginModel2) : Result<Unit>

}