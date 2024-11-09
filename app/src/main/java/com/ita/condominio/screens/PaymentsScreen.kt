
package com.ita.condominio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.CustomHeader
import kotlinx.coroutines.launch

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
    // Estado para los adeudos
    val adeudos = remember { mutableStateOf(emptyList<Adeudo>()) }

    // Simulamos la carga de los adeudos al inicio
    LaunchedEffect(Unit) {
        // Aquí simulamos obtener los adeudos de la casa con número "123", puedes reemplazarlo por la lógica real
        adeudos.value = morososRepository.getAdeudosByCasa("123") // Ejemplo con un número de casa
    }

    // Calculamos el monto pendiente sumando los adeudos
    val pendingAmount by remember { derivedStateOf { adeudos.value.sumOf { it.cantidad } } }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Encabezado de la pantalla
        CustomHeader(title = "Pagos")

        // Contenido principal de la pantalla
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp) // Espacio horizontal
                .weight(1f), // Permite que este Box use el espacio restante
            contentAlignment = Alignment.TopCenter // Centra el contenido
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Texto para indicar la cantidad pendiente
                Text(
                    text = "Pendiente de pagar",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 10.dp)
                )

                // Muestra la cantidad pendiente
                DisplayAmount(pendingAmount = pendingAmount)

                // Agrega el botón de pago justo debajo de la cantidad pendiente
                Button(
                    onClick = { navController.navigate("paypal/${pendingAmount}") }, // Navegar a PayPalScreen con el monto pendiente
                    modifier = Modifier
                        .padding(top = 16.dp) // Espacio entre el monto y el botón
                        .height(48.dp)
                        .fillMaxWidth()
                        .background(Color(0xFFC4D9D2), RoundedCornerShape(8.dp)),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC4D9D2),
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "Paga en línea", fontSize = 18.sp)
                }

                // Desglose de la información del pago
                Spacer(modifier = Modifier.height(32.dp)) // Espacio entre el botón y el desglose
            }
        }

        // Card que contiene los adeudos
        Card(
            modifier = Modifier
                .padding(16.dp) // Márgenes alrededor de la tarjeta
                .fillMaxWidth() // Hace que la tarjeta tenga el ancho completo del dispositivo
                .align(Alignment.CenterHorizontally), // Centra la tarjeta
            shape = RoundedCornerShape(16.dp), // Esquinas redondeadas
            elevation = 8.dp // Sombra para dar efecto de elevación
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp) // Espacio dentro de la tarjeta
                    .fillMaxWidth()
            ) {
                // Título de los adeudos
                Text(
                    text = "Tus Adeudos:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                // Lista de adeudos
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    items(adeudos.value) { adeudo ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp) // Ajuste de espacio alrededor de cada fila
                                .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = adeudo.descripcion,
                                modifier = Modifier.weight(1f).padding(end = 16.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "$${adeudo.cantidad}",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }

        // Barra de navegación inferior
        BottomNavigationBar(navController = navController)
    }
}

// Método que acepta un argumento y muestra el pendiente a pagar
@Composable
fun DisplayAmount(pendingAmount: Double) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp), // Espacio entre el texto de la cantidad y el contenido superior
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$ $pendingAmount", // Muestra la cantidad
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
