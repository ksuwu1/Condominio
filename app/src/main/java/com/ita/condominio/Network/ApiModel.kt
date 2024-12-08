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

data class MaintenanceIncome(
    val M_folio: Int,
    val casa: Int,
    val nombre: String,
    val mes: String,
    val cantidad: Int,
    val transferencia: Boolean
)

data class ReservationIncome(
    val R_folio: Int,
    val casa: Int,
    val descripcion: String,
    val fecha: String,
    val cantidad: Int,
    val transferencia: Boolean
)

data class Expense(
    val E_folio: Int,
    val descripcion: String,
    val fecha: String,
    val cantidad: Int
)


data class LoginRequest(
    val correo: String,
    val password: String
)

data class UserRequest(
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
