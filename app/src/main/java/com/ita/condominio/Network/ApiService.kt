package com.ita.condominio.Network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("users/login")
    suspend fun loginUser(@Body user: LoginRequest): Response<UserResponse>

    @GET("users")
    suspend fun getUsers(): Response<List<UserResponse>>

    @GET("morosos")
    suspend fun getMorosos(): Response<List<Moroso>>

    @GET("avisos") // El endpoint de la API
    suspend fun getAvisos(): Response<List<ModelAvisos>>

    //Reservaciones
    @POST("reservations")
    fun insertReservation(@Body reservation: Reservation): Call<ReservationResponse>

}
