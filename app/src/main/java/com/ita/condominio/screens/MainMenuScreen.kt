package com.ita.condominio

import androidx.compose.foundation.Image
import androidx. compose. material. ButtonColors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.ita.condominio.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.runtime.SideEffect


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    var showAvisoDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxWidth().fillMaxWidth(),
        bottomBar = { BottomNavigationBar(navController) },
        containerColor = Color.White // Color de fondo del Scaffold
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Usa el padding interno del Scaffold
        ) {
            // Banner en la parte superior
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Primera barra divisora con texto "Condominio"
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

                // Segunda barra divisora con texto dinámico
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(Color(0xFFC4D9D2)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Circuito --- Int ###",  // Texto dinámico
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }

            // Contenido de la LazyColumn
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Avisos del condominio",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.verde_oscuro),
                        )
                    }
                }
                item {
                    BoxWithImageBackground(
                        imageRes = R.drawable.avisos,
                        overlayColor = colorResource(id = R.color.verde_oscuro),
                        text = "Avisos",
                        textColor = colorResource(id = R.color.verde_blanco),
                        onClick = { navController.navigate("notices") }
                    )
                }
                item {
                    // Finanzas section
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Finanzas",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.verde_oscuro),
                        )
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        MonthCarousel(onMonthClick = {
                            navController.navigate("informe")
                        })
                    }
                }
                item {
                    // Reservar área section
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Reservar área",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.verde_oscuro),
                        )
                    }
                }
                item {
                    BoxWithImageBackground(
                        imageRes = R.drawable.reservar,
                        overlayColor = colorResource(id = R.color.verde_oscuro),
                        text = "Reservar",
                        textColor = colorResource(id = R.color.verde_blanco),
                        onClick = { navController.navigate("reservation") }
                    )
                }
                item {
                    // Morosos section
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Morosos",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.verde_oscuro),
                        )
                    }
                }
                item {
                    BoxWithImageBackground(
                        imageRes = R.drawable.morosos,
                        overlayColor = colorResource(id = R.color.verde_oscuro),
                        text = "Morosos",
                        textColor = colorResource(id = R.color.verde_blanco),
                        onClick = { navController.navigate("morosos") }
                    )
                }
            }
        }

        // Diálogo de avisos
        AvisoDialog(showDialog = showAvisoDialog, onDismiss = { showAvisoDialog = false })
    }
}

@Composable
fun BoxWithImageBackground(
    imageRes: Int,
    overlayColor: Color,
    text: String,
    textColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center // Alinea el contenido en el centro del Box
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(15.dp))
        )
        // Capa de color semitransparente
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(overlayColor.copy(alpha = 0.5f))
                .clip(RoundedCornerShape(15.dp))
        )
        // Texto centrado
        Text(
            text = text,
            color = textColor,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AvisoDialog(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        androidx.compose.material.AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                androidx.compose.material.Text(
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
                        androidx.compose.material.Text(
                            text = aviso,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            },
            confirmButton = {
                androidx.compose.material.Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(Color(0xFF9AB4A3)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    androidx.compose.material.Text(
                        text = "Cerrar",
                        color = Color.White
                    )
                }
            }
        )
    }
}

@Composable
fun MonthCarousel(onMonthClick: () -> Unit) { // Cambia (String) a ()
    val months = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")

    LazyRow(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        items(months) { month ->
            Button(
                onClick = { onMonthClick() }, // Solo llama a onMonthClick sin argumentos
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    //.size(120.dp) // Hacer que el botón sea cuadrado (60x60)
                    .wrapContentWidth()
                    .background(Color(0xFFC4DAD2))
                    .height(155.dp)
                    .width(155.dp),
                shape = RoundedCornerShape(10.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5c7c74))

            ) {
                Text(
                    text = month,
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


