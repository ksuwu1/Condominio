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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import com.ita.condominio.Network.Expense
import com.ita.condominio.Network.InformeViewModel
import com.ita.condominio.Network.MaintenanceIncome
import com.ita.condominio.Network.ReservationIncome

@Composable
fun InformeScreen(navController: NavHostController, viewModel: InformeViewModel = viewModel()) {
    // Disparar la carga de datos al iniciar la pantalla
    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    // Observamos los datos del ViewModel
    val expenses by viewModel.expenses.collectAsState()
    val maintenanceIncomes by viewModel.maintenanceIncomes.collectAsState()
    val reservationIncomes by viewModel.reservationIncomes.collectAsState()

    // Calcular totales dinámicamente
    val maintenanceTotal = maintenanceIncomes.sumOf { it.cantidad }
    val reservationTotal = reservationIncomes.sumOf { it.cantidad }
    val expensesTotal = expenses.sumOf { it.cantidad }
    val finalTotal = maintenanceTotal + reservationTotal - expensesTotal

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            // Encabezado (igual que antes)
            Header(navController)
        }

        item {
            Text(
                text = "Ingresos de mantenimiento",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(maintenanceIncomes) { item ->
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
        items(reservationIncomes) { item ->
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
        items(expenses) { item ->
            ExpenseCard(item = item)
        }

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
fun Header(navController: NavHostController) {
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
            Text(text = "Folio: ${item.M_folio}", fontWeight = FontWeight.Bold)
            Text(text = "Casa: ${item.casa}")
            Text(text = "Nombre: ${item.nombre}")
            Text(text = "Mes: ${item.mes}")
            Text(text = "Cantidad: \$${item.cantidad}")
            if(item.transferencia){
                Text(text = "Transferencia: SI")
            }else{
                Text(text = "Transferencia: NO")
            }
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
            Text(text = "Folio: ${item.R_folio}", fontWeight = FontWeight.Bold)
            Text(text = "Casa: ${item.casa}")
            Text(text = "Descripción: ${item.descripcion}")
            Text(text = "Fecha: ${item.fecha}")
            Text(text = "Cantidad: \$${item.cantidad}")
            if(item.transferencia){
                Text(text = "Transferencia: SI")
            }else{
                Text(text = "Transferencia: NO")
            }
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
            Text(text = "Folio: ${item.E_folio}", fontWeight = FontWeight.Bold)
            Text(text = "Descripción: ${item.descripcion}")
            Text(text = "Fecha: ${item.fecha}")
            Text(text = "Cantidad: \$${item.cantidad}")
        }
    }
}
