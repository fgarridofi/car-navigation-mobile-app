package com.example.formapp.domain.repository

import com.example.formapp.data.Result
import com.example.formapp.domain.model.Component
import com.example.formapp.domain.model.Historial

interface HistorialRepository {
    suspend fun insertHistorial (historial: Historial): Result<Unit>

    suspend fun getHistorial () : Result<List<Historial>>
}