package com.ita.condominio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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

@OptIn(ExperimentalMaterial3Api::class)
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
        ReservationIncome("001", "1", "Sala de eventos", "01-10-2023", 2000, "Sí"),
        ReservationIncome("002", "2", "Cancha de tenis", "05-10-2023", 1500, "No")
    )
    val expensesData = listOf(
        Expense("001", "Mantenimiento", "01-10-2023", 2500),
        Expense("002", "Reparaciones", "10-10-2023", 1000)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        // Banner
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
                Text(
                    text = "Condominio",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp),
                    color = Color.White
                )
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

        // Saldo del mes anterior
        Text(
            text = "Saldo del mes anterior: $$totalIncome",
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )

        // Tabla de ingresos de mantenimiento
        Text(
            text = "Ingresos de mantenimiento",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        MaintenanceIncomeTable(data = maintenanceIncomeData)

        // Tabla de ingresos de reservaciones
        Text(
            text = "Ingresos de reservaciones",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        ReservationIncomeTable(data = reservationIncomeData)

        // Tabla de egresos
        Text(
            text = "Egresos",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        ExpensesTable(data = expensesData)
    }
}

@Composable
fun MaintenanceIncomeTable(data: List<MaintenanceIncome>) {
    LazyColumn {
        // Encabezados de la tabla
        item {
            LazyRow {
                items(listOf("Folio", "No. Casa", "Nombre", "Mes", "Cantidad", "Transferencia")) { title ->
                    Text(title, modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
                }
            }
        }

        items(data) { item ->
            LazyRow {
                items(listOf(item.folio, item.noCasa, item.nombre, item.mes, item.cantidad.toString(), item.transferencia)) { value ->
                    Text(value, modifier = Modifier.padding(8.dp))
                }
            }
        }

        // Sumar las cantidades
        val total = data.sumOf { it.cantidad }
        item {
            Text("Total: $$total", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun ReservationIncomeTable(data: List<ReservationIncome>) {
    LazyColumn {
        // Encabezados de la tabla
        item {
            LazyRow {
                items(listOf("Folio", "No. Casa", "Descripción", "Fecha", "Cantidad", "Transferencia")) { title ->
                    Text(title, modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
                }
            }
        }

        items(data) { item ->
            LazyRow {
                items(listOf(item.folio, item.noCasa, item.descripcion, item.fecha, item.cantidad.toString(), item.transferencia)) { value ->
                    Text(value, modifier = Modifier.padding(8.dp))
                }
            }
        }

        // Sumar las cantidades
        val total = data.sumOf { it.cantidad }
        item {
            Text("Total: $$total", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun ExpensesTable(data: List<Expense>) {
    LazyColumn {
        // Encabezados de la tabla
        item {
            LazyRow {
                items(listOf("Folio", "Descripción", "Fecha", "Cantidad")) { title ->
                    Text(title, modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
                }
            }
        }

        items(data) { item ->
            LazyRow {
                items(listOf(item.folio, item.descripcion, item.fecha, item.cantidad.toString())) { value ->
                    Text(value, modifier = Modifier.padding(8.dp))
                }
            }
        }

        // Sumar las cantidades
        val total = data.sumOf { it.cantidad }
        item {
            Text("Total: $$total", modifier = Modifier.padding(16.dp))
        }
    }
}

// Datos de ejemplo
data class MaintenanceIncome(val folio: String, val noCasa: String, val nombre: String, val mes: String, val cantidad: Int, val transferencia: String)
data class ReservationIncome(val folio: String, val noCasa: String, val descripcion: String, val fecha: String, val cantidad: Int, val transferencia: String)
data class Expense(val folio: String, val descripcion: String, val fecha: String, val cantidad: Int)
