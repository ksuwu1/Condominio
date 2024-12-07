package com.ita.condominio.Models

data class ModelMorosos(
    val id_moroso: Int,
    val casa: Int,
    val descripcion: String,
    val detalleDescripcion: String,
    val cantidad: Int
)
