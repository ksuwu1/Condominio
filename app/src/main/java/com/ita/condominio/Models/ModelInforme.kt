package com.ita.condominio.Models

data class MaintenanceIncome(
    val folio: String,
    val noCasa: String,
    val nombre: String,
    val mes: String,
    val cantidad: Int,
    val transferencia: String
)

data class ReservationIncome(
    val folio: String,
    val noCasa: String,
    val descripcion: String,
    val fecha: String,
    val cantidad: Int,
    val transferencia: String
)

data class Expense(
    val folio: String,
    val descripcion: String,
    val fecha: String,
    val cantidad: Int
)
