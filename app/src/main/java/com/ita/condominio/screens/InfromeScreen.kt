package com.ita.condominio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ita.condominio.Models.MaintenanceIncome
import com.ita.condominio.Models.ReservationIncome
import com.ita.condominio.Models.Expense
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun InformeScreen(navController: NavHostController) {
    val month = "Marzo"
    val totalIncome = 15750
    val maintenanceIncomeData = listOf(
        MaintenanceIncome("001", "1", "Juan Pérez", month, 5000, "Sí"),
        MaintenanceIncome("002", "2", "María López", month, 6000, "No"),
        MaintenanceIncome("003", "3", "Carlos García", month, 6750, "Sí")
    )
    val reservationIncomeData = listOf(
        ReservationIncome("004", "4", "Sala de eventos", "01-10-2023", 2000, "Sí"),
        ReservationIncome("005", "5", "Cancha de tenis", "05-10-2023", 1500, "No")
    )
    val expensesData = listOf(
        Expense("006", "Mantenimiento", "01-10-2023", 2500),
        Expense("007", "Reparaciones", "10-10-2023", 1000)
    )

    val maintenanceTotal = maintenanceIncomeData.sumOf { it.cantidad }
    val reservationTotal = reservationIncomeData.sumOf { it.cantidad }
    val expensesTotal = expensesData.sumOf { it.cantidad }
    val finalTotal = totalIncome + maintenanceTotal + reservationTotal - expensesTotal

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        // Banner con flecha de regreso
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color(0xFF699C89)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Regresar",
                                tint = Color.White
                            )
                        }
                        Text(
                            text = "Condominio",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp),
                            color = Color.White
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(Color(0xFFC4D9D2)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Informe del mes: $month",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }
        }

        // Saldo del mes anterior
        item {
            Text(
                text = "Saldo del mes anterior: $$totalIncome",
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Secciones con tarjetas
        item {
            Text(
                text = "Ingresos de mantenimiento",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(maintenanceIncomeData) { item ->
            MaintenanceIncomeCard(item = item)
        }

        item {
            Text(
                text = "Ingresos de reservaciones",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(reservationIncomeData) { item ->
            ReservationIncomeCard(item = item)
        }

        item {
            Text(
                text = "Egresos",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(expensesData) { item ->
            ExpenseCard(item = item)
        }

        // Total del mes
        item {
            Text(
                text = "Total del mes: $$finalTotal",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20), // Color verde oscuro
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun MaintenanceIncomeCard(item: MaintenanceIncome) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Folio: ${item.folio}", fontWeight = FontWeight.Bold)
            Text(text = "Casa: ${item.noCasa}")
            Text(text = "Nombre: ${item.nombre}")
            Text(text = "Mes: ${item.mes}")
            Text(text = "Cantidad: \$${item.cantidad}")
            Text(text = "Transferencia: ${item.transferencia}")
        }
    }
}

@Composable
fun ReservationIncomeCard(item: ReservationIncome) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Folio: ${item.folio}", fontWeight = FontWeight.Bold)
            Text(text = "Casa: ${item.noCasa}")
            Text(text = "Descripción: ${item.descripcion}")
            Text(text = "Fecha: ${item.fecha}")
            Text(text = "Cantidad: \$${item.cantidad}")
            Text(text = "Transferencia: ${item.transferencia}")
        }
    }
}

@Composable
fun ExpenseCard(item: Expense) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCDD2)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Folio: ${item.folio}", fontWeight = FontWeight.Bold)
            Text(text = "Descripción: ${item.descripcion}")
            Text(text = "Fecha: ${item.fecha}")
            Text(text = "Cantidad: \$${item.cantidad}")
        }
    }
}
