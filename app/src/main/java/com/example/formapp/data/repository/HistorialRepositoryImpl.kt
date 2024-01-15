package com.example.formapp.data.repository

import com.bumptech.glide.load.HttpException
import com.example.formapp.R
import com.example.formapp.data.Result
import com.example.formapp.data.source.FormApi
import com.example.formapp.domain.model.Historial
import com.example.formapp.domain.repository.HistorialRepository
import com.example.formapp.domain.repository.UserRepository
import com.example.formapp.util.UiText
import java.io.IOException
import javax.inject.Inject

class HistorialRepositoryImpl @Inject constructor(private val api: FormApi): HistorialRepository {
    override suspend fun insertHistorial(historial: Historial): Result<Unit> {
        val response = try {
            api.insertHistorial(historial)
        } catch (e: retrofit2.HttpException) {
            return Result.Error(UiText.StringResource(R.string.unknown_exception_error))
        } catch (e: IOException) {
            return Result.Error(UiText.StringResource(R.string.io_exception_error))
        }
        return Result.Success(data = response, message = UiText.StringResource(R.string.component_created_successfully))
    }

    override suspend fun getHistorial(): Result<List<Historial>> {
        return try {
            val historial = api.getHistorial()
            Result.Success(historial)
        } catch (e: HttpException) {
            Result.Error(UiText.StringResource(R.string.io_exception_error))
        } catch (e: IOException) {
            Result.Error(UiText.StringResource(R.string.io_exception_error))
        }
    }
}