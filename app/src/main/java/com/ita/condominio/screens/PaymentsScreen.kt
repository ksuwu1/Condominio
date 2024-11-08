package com.ita.condominio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.CustomHeader

@Composable
fun PaymentsScreen(navController: NavHostController) {
    val pendingAmount = 100.00 // Cambia esto por el monto que necesitas
    val paymentConcept = "Reserva en Condominio" // Concepto de pago

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                    modifier = Modifier.padding(top = 16.dp)
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
                PaymentDetails(
                    amount = pendingAmount,
                    concept = paymentConcept
                )
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

@Composable
fun PaymentDetails(amount: Double, concept: String) {
    Box(
        modifier = Modifier
            .fillMaxSize() // Ocupa todo el espacio disponible
            .padding(16.dp), // Opcional: agrega un margen alrededor del contenido
        contentAlignment = Alignment.Center // Centra el contenido en el medio
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center, // Asegura que el contenido esté centrado verticalmente
            modifier = Modifier.fillMaxWidth()
        ) {
            // Total a pagar, centrado
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Total a pagar: $$amount",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp),
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre Total a pagar y Concepto

            // Concepto, también centrado
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Concepto: $concept",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}


// Función de previsualización
@Preview(showBackground = true)
@Composable
fun PaymentsScreenPreview() {
    val navController = rememberNavController() // Crea un NavHostController simulado
    PaymentsScreen(navController = navController)
}
