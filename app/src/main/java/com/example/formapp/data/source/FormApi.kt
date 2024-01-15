package com.example.formapp.data.source

import com.example.formapp.domain.model.Component
import com.example.formapp.domain.model.Historial
import com.example.formapp.domain.model.LoginModel2
import com.example.formapp.domain.model.User
import retrofit2.http.*

interface FormApi {

    @POST("user")
    suspend fun insertUser(@Body user: User)

    @POST("authenticate")
    suspend fun authenticateUser (@Body  loginModel: LoginModel2)

    @POST("componente")
    suspend fun insertComponent(@Body component: Component)

    @GET("components")
    suspend fun getComponents() : List<Component>

    @PUT("component/{id}")
    suspend fun updateComponent(@Path("id") id: String, @Body component: Component)

    @DELETE("component/{id}")
    suspend fun deleteComponent(@Path("id") id: String)

    @POST("historial")
    suspend fun insertHistorial(@Body historial: Historial)

    @GET("historial")
    suspend fun getHistorial() : List<Historial>

}