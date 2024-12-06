package com.ita.condominio.Network

data class UserResponse(
    val id_usuario: Int,
    val nombre: String,
    val apellido_pat: String,
    val apellido_mat: String,
    val num_casa: Int,
    val correo: String,
    val tel_casa: Long,
    val cel: Long
)

data class LoginRequest(
    val correo: String,
    val password: String
)