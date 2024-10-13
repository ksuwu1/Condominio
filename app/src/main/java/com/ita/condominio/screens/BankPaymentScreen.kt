package com.ita.condominio.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun BankPaymentScreen(navController: NavHostController, total: Double) {
    // Contenido de la pantalla de PayPal
    Text(text = "Pantalla de BankPayment", fontSize = 24.sp)
}