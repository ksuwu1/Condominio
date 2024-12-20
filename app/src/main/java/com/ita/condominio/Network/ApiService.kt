package com.ita.condominio.Network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("users/login")
    suspend fun loginUser(@Body user: LoginRequest): Response<UserResponse>

    @GET("users")
    suspend fun getUsers(): Response<List<UserResponse>>

    @GET("morosos")
    suspend fun getMorosos(): Response<List<Moroso>>

    @GET("avisos") // El endpoint de la API
    suspend fun getAvisos(): Response<List<ModelAvisos>>
        
    @GET("users")
    suspend fun getUserByEmail(@Query("correo") correo: String): Response<UserResponse>

    @GET("/egreso")
    suspend fun getExpenses(): List<Expense>

    @GET("/ingreso/mantenimiento")
    suspend fun getMaintenanceIncomes(): List<MaintenanceIncome>

    @GET("/ingreso/reserva")
    suspend fun getReservationIncomes(): List<ReservationIncome>
    @PATCH("users/{id}/password")
    suspend fun changePassword(
        @Path("id") userId: Int,
        @Body passwordRequest: ChangePasswordRequest
    ): Response<Void>
        
        @POST("reservaciones")
    suspend fun insertarReservaciones(@Body reservacion: Reservacion): ReservacionRespuesta
}
