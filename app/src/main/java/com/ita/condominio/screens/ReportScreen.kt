package com.ita.condominio.screens

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.R
import androidx.compose.material.AlertDialog


@Composable
fun ReportScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Primera barra divisora con texto "Condominio"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xFF699C89)),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "CONDOMINIO",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp),
                color = Color.White
            )
        }

        // Segunda barra divisora con texto "Mi cuenta"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color(0xFFC4D9D2)),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "INFORMES",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        var showReservacionDialog by remember { mutableStateOf(false) }
        var showAvisoDialog by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.weight(1F).padding(16.dp)
        ) {
            SectionWithTitleButton(
                title = "Avisos del condómino",
                imageRes = R.drawable.chat, // Reemplaza con tu ícono
                onClick = {showAvisoDialog = true}
            )

            Text(
                text = "Finanzas del condominio",
                fontSize = 20.sp, // Tamaño de la fuente
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 16.dp) // Espacio vertical alrededor del texto
                    .align(Alignment.Start) // Alinear a la izquierda
            )

            MonthCarousel(onMonthClick = { month ->
                // Aquí puedes manejar la acción que deseas al hacer clic en un mes
                println("Mes seleccionado: $month") // Por ejemplo, imprimir el mes
            })

            SectionWithTitleButton(
                title = "Reservación",
                imageRes = R.drawable.home, // Reemplaza con tu ícono
                onClick = {showReservacionDialog = true}
            )

            SectionWithTitleButton(
                title = "Morosos",
                imageRes = R.drawable.reporte, // Reemplaza con tu ícono
                onClick = {navController.navigate("morosos")}
            )

            AvisoDialog(showDialog = showAvisoDialog, onDismiss = { showAvisoDialog = false })
            ReservationDialog(showDialog = showReservacionDialog, onDismiss = { showReservacionDialog = false })
        }

        // Bottom Navigation
        BottomNavigationBar(navController)
    }
}

@Composable
fun SectionWithTitleButton(title: String, imageRes: Int, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.Start) { // Alinear a la izquierda
        Text(
            text = title,
            fontSize = 20.sp, // Aumentar el tamaño de la fuente
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.Start) // Alinear el texto a la izquierda
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espacio adicional entre el texto y el botón

        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF9AB4A3))
        ) {
            Icon(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }
    }
}

@Composable
fun MonthCarousel(onMonthClick: (String) -> Unit) {
    val months = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")

    LazyRow(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        items(months) { month ->
            Button(
                onClick = { onMonthClick(month) },
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .wrapContentWidth() // Ajustar el ancho al contenido
                    .height(50.dp), // Ajustar la altura si es necesario
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9AB4A3)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = month, color = Color.White, fontSize = 18.sp, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun AvisoDialog(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "AVISO",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth() // Centrar el título
                )
            },
            text = {
                Column {
                    // Lista de avisos aleatorios
                    val avisos = listOf(
                        "Aviso 1: Reunión el viernes a las 7 PM.",
                        "Aviso 2: Pago de mantenimiento hasta el 10 de cada mes.",
                        "Aviso 3: Prohibido el uso de parrillas en el balcón.",
                        "Aviso 4: Fiesta de vecinos el próximo sábado.",
                        "Aviso 5: Inscripción para clases de yoga abiertas."
                    )

                    for (aviso in avisos) {
                        Text(text = aviso, modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(Color(0xFF9AB4A3)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Cerrar",
                        color = Color.White
                    )
                }
            }
        )
    }
}

@Composable
fun ReservationDialog(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {

            },
            text = {
                val reservedDates = listOf(
                    "2024-10-15: Palapa",
                    "2024-10-20: Salón",
                    "2024-10-25: Baños",
                    "2024-11-05: Palapa",
                    "2024-11-10: Salón"
                )

                ReservationList(reservedDates)
            },
            confirmButton = {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(Color(0xFF9AB4A3)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Cerrar",
                        color = Color.White
                    )
                }
            }
        )
    }
}

@Composable
fun ReservationList(reservedDates: List<String>) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Título de la lista
        Text(
            text = "Reservaciones",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Separador
        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))

        LazyColumn {
            items(reservedDates) { reservation ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth(),
                    backgroundColor = Color(0xFF9AB4A3),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        val (date, location) = reservation.split(":")

                        Text(
                            text = "Fecha: $date",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Ubicación: $location",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}
