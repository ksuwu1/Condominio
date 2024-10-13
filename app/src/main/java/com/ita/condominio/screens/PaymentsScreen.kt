package com.ita.condominio.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun PaymentsScreen(navController: NavHostController, total: Int) {
    // Estados para cada checkbox
    var banosChecked by remember { mutableStateOf(false) }
    var palapaChecked by remember { mutableStateOf(false) }
    var salonChecked by remember { mutableStateOf(false) }
    var albercaChecked by remember { mutableStateOf(false) }

    // Agrupar todo en un Box
    Box(
        modifier = Modifier
            .fillMaxSize() // Llenar toda la pantalla
            .padding(16.dp), // Padding externo
        contentAlignment = Alignment.Center // Alinear el contenido al centro
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Box que contiene el contenido principal
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(2.dp, Color(0xFFC4D9D2))) // Cambiar color del borde
                    .padding(16.dp) // Padding interno del Box
            ) {
                // Layout de la pantalla
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Título centrado
                    Text(
                        text = "Espacio a reservar",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    // Fila de Checkboxes
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row {
                                Checkbox(
                                    checked = banosChecked,
                                    onCheckedChange = { banosChecked = it }
                                )
                                Text(text = "Baños")
                            }
                            Row {
                                Checkbox(
                                    checked = salonChecked,
                                    onCheckedChange = { salonChecked = it }
                                )
                                Text(text = "Salón")
                            }
                        }

                        Column {
                            Row {
                                Checkbox(
                                    checked = palapaChecked,
                                    onCheckedChange = { palapaChecked = it }
                                )
                                Text(text = "Palapa")
                            }
                            Row {
                                Checkbox(
                                    checked = albercaChecked,
                                    onCheckedChange = { albercaChecked = it }
                                )
                                Text(text = "Alberca")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre el Box y el total a pagar

            // Componente Total a Pagar
            Total(total = total)
        }
    }
}

@Composable
fun Total(total: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Total a pagar: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = "$$total", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentsScreenPreview() {
    // Llamar a PaymentsScreen con un total ficticio para el preview
    PaymentsScreen(navController = rememberNavController(), total = 123)
}
