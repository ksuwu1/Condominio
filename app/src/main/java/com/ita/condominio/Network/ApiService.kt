package com.ita.condominio.Network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users/login") // Asegúrate de poner la ruta correcta de tu API
    suspend fun loginUser(@Body user: LoginRequest): Response<UserResponse>
}