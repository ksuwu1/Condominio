package com.ita.condominio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.CustomHeader
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext

// Modelo para los adeudos
data class Adeudo(
    val descripcion: String,
    val cantidad: Double
)

// Repositorio simulado para obtener los adeudos
object morososRepository {
    fun getAdeudosByCasa(numeroCasa: String): List<Adeudo> {
        // Aquí se simula obtener los adeudos, debes reemplazar esto con tu lógica real
        return listOf(
            Adeudo("Pago de mantenimiento", 50.0),
            Adeudo("Pago de servicios", 30.0)
        )
    }
}

@Composable
fun PaymentsScreen(navController: NavHostController) {
    val adeudos = remember { mutableStateOf(emptyList<Adeudo>()) }

    LaunchedEffect(Unit) {
        adeudos.value = morososRepository.getAdeudosByCasa("123")
    }

    val pendingAmount by remember { derivedStateOf { adeudos.value.sumOf { it.cantidad } } }

    val bankName = "Banco Ejemplo"
    val accountNumber = "1234567890"
    val referenceNumber = "REF12345"
    val nombreCompleto = "Juan Pérez"
    val context = LocalContext.current

    Scaffold(
        topBar = { CustomHeader(title = "Pagos") },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Pendiente de pagar",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 10.dp)
                    )

                    DisplayAmount(pendingAmount)

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { navController.navigate("paypal/${pendingAmount}") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFC4D9D2),
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Paga en línea", fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            PDFGenerator.generarFichaPDF(
                                context = context,
                                bankName = bankName,
                                accountNumber = accountNumber,
                                referenceNumber = referenceNumber,
                                nombreCompleto = nombreCompleto,
                                totalAmount = pendingAmount
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFC4D9D2),
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Paga por referencia bancaria", fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }

                item {
                    // Tarjeta de adeudos
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = 8.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Tus Adeudos:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Lista de adeudos dentro de la tarjeta
                            adeudos.value.forEach { adeudo ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .background(
                                            Color(0xFFe6f2ee), // Color de fondo
                                            RoundedCornerShape(8.dp)
                                        )
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = adeudo.descripcion,
                                        modifier = Modifier.weight(1f),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "$${adeudo.cantidad}",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayAmount(pendingAmount: Double) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$ $pendingAmount",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 50.sp)
        )
    }
}


// Función de previsualización
@Preview(showBackground = true)
@Composable
fun PaymentsScreenPreview() {
    val navController = rememberNavController() // Crea un NavHostController simulado
    PaymentsScreen(navController = navController)
}
