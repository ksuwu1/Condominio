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
import com.ita.condominio.CustomHeader

@Composable
fun PaymentsScreen(navController: NavHostController) {

    Text("Pantalla de Detalles de Cuenta")
}
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Encabezado de la pantalla
        CustomHeader(title = "Pagos")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(100.dp) // Opcional: agrega un poco de padding
    ) {
        Column(
            modifier = Modifier.fillMaxSize(), // Usamos Column para apilar el contenido
            verticalArrangement = Arrangement.Top, // Coloca el contenido en la parte superior
            horizontalAlignment = Alignment.CenterHorizontally // Centra el contenido horizontalmente
        ) {
            // Texto para indicar la cantidad pendiente
            Text(
                text = "Pendiente de pagar",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp) // Espacio entre el encabezado y el texto
            )

            // Muestra la cantidad pendiente
            DisplayAmount(pendingAmount = 0.00)

            // Agrega un Column para los botones
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp), // Espacio entre la cantidad y los botones
                horizontalAlignment = Alignment.CenterHorizontally // Centra los botones
            ) {

                Button(
                    onClick = { navController.navigate("paypal") }, // Navegar a PayPalScreen
                    modifier = Modifier
                        .padding(vertical = 8.dp) // Espacio vertical entre los botones
                        .height(48.dp) // Altura del botón
                        .background(Color(0xFFC4D9D2), shape = RoundedCornerShape(8.dp)), // Color de fondo del botón
                    shape = RoundedCornerShape(8.dp) // Bordes redondeados
                ) {
                    Text(text = "Paga en línea")
                }
            }
        }
    }
}

// Método que acepta un argumento y muestra el pendiente a pagar
@Composable
fun DisplayAmount(pendingAmount: Double) {
    Text(
        text = "$ $pendingAmount", // Muestra la cantidad
        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 50.sp), // Cambia el tamaño de la fuente aquí
        modifier = Modifier.padding(top = 8.dp) // Espacio entre el texto de la cantidad y el contenido superior
    )
}

// Función de previsualización
@Preview(showBackground = true)
@Composable
fun PaymentsScreenPreview() {
    val navController = rememberNavController() // Crea un NavHostController simulado
    PaymentsScreen(navController = navController)
}

