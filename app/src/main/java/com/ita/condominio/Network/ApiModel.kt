package com.ita.condominio.Network

data class UserResponse(
    val id_usuario: Int,
    val nombre: String,
    val apellido_pat: String,
    val apellido_mat: String,
    val num_casa: Int,
    val correo: String,
    val password: String,
    val tel_casa: String,
    val cel: String
)

data class Moroso(
    val id_moroso: Int,
    val casa: Int,
    val descripcion_fecha: String,
    val detalle: String,
    val cantidad: Int
)

data class ModelAvisos(
    val id_aviso: Int,
    val tipo_aviso: String,
    val titulo: String,
    val fecha: String,
    val descripcion: String
)


data class LoginRequest(
    val correo: String,
    val password: String
)

data class UserRequest(
    val nombre: String,
    val apellido_pat: String,
    val apellido_mat: String,
    val tel_casa: String,
    val cel: String,
    val correo: String
)
