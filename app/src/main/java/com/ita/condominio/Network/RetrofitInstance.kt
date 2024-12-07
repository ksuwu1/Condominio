package com.ita.condominio.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.ita.condominio.Network.ApiService

object RetrofitInstance {

    // URL base de la API
    private const val BASE_URL = "https://apicondominio.onrender.com/"

    // Crear la instancia de Retrofit
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }


}