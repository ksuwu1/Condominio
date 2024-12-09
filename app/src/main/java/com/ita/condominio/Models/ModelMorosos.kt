package com.ita.condominio.Models

data class ModelMorosos(
    val id_moroso: Int,
    val casa: Int,
    val descripcion: String,
    val detalleDescripcion: String,
    val cantidad: Int
)

data class ModelUser(
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