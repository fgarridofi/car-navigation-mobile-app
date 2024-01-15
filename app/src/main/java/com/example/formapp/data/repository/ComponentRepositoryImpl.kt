package com.example.formapp.data.repository

import android.util.Log
import com.bumptech.glide.load.HttpException
import com.example.formapp.R
import com.example.formapp.data.Result
import com.example.formapp.data.source.FormApi
import com.example.formapp.domain.model.Component
import com.example.formapp.domain.repository.ComponentRepository
import com.example.formapp.util.UiText
import java.io.IOException
import javax.inject.Inject

class ComponentRepositoryImpl @Inject constructor(private val api: FormApi): ComponentRepository {
    override suspend fun insertComponent(componet: Component): Result<Unit> {
        val response = try {
            api.insertComponent(componet)
        } catch (e: retrofit2.HttpException) {
            return Result.Error(UiText.StringResource(R.string.unknown_exception_error))
        } catch (e: IOException) {
            return Result.Error(UiText.StringResource(R.string.io_exception_error))
        }
        return Result.Success(data = response, message = UiText.StringResource(R.string.component_created_successfully))
    }

    override suspend fun getComponets():  Result<List<Component>> {
        return try {
            val components = api.getComponents()
            Result.Success(components)
        } catch (e: HttpException) {
            Result.Error(UiText.StringResource(R.string.io_exception_error))
        } catch (e: IOException) {
            Result.Error(UiText.StringResource(R.string.io_exception_error))
        }
    }

    override suspend fun updateComponent(id: String, component: Component): Result<Unit> {
        return try {
            api.updateComponent(id, component)
            Result.Success(Unit, UiText.StringResource(R.string.component_updated_successfully))
        } catch (e: HttpException) {
            Result.Error(UiText.StringResource(R.string.unknown_exception_error))
        } catch (e: IOException) {
            Result.Error(UiText.StringResource(R.string.io_exception_error))
        }
    }

    override suspend fun deleteComponent(id: String): Result<Unit> {
        Log.e("id",id)
        return try {
            api.deleteComponent(id)
            Result.Success(Unit, UiText.StringResource(R.string.component_deleted_successfully))
        } catch (e: HttpException) {
            Result.Error(UiText.StringResource(R.string.unknown_exception_error))
        } catch (e: IOException) {
            Result.Error(UiText.StringResource(R.string.io_exception_error))
        }
    }
}