package com.ita.condominio.Network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("users/login")
    suspend fun loginUser(@Body user: LoginRequest): Response<UserResponse>

    @GET("users")
    suspend fun getUsers(): Response<List<UserResponse>>
}
