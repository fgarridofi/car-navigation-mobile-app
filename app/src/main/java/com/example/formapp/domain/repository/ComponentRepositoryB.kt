package com.example.formapp.domain.repository

import com.example.formapp.data.Result
import com.example.formapp.domain.model.Component

interface ComponentRepositoryB {

    suspend fun insertComponent(componet: Component): Result<Unit>

    suspend fun getComponets () :  Result<List<Component>>

    suspend fun updateComponent(id: String, component: Component): Result<Unit>

    suspend fun deleteComponent(id: String): Result<Unit>

}