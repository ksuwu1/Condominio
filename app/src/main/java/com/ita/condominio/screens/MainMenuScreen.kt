package com.ita.condominio

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    // Estado para controlar la visibilidad del diálogo
    var showDialog by remember { mutableStateOf(false) }
    var editableText by remember { mutableStateOf("Texto de avisos inicial") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Condominio",
                            fontSize = 27.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.verde_blanco),
                        )

                        Text(
                            text = "Circuito \"nombre\"",
                            fontSize = 16.sp,
                            color = Color.LightGray,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF85A69F))
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                    onClick = { showDialog = true } // Abre el diálogo al hacer clic
                )
            }
            item {
                // Finanzas section
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center // Alinea el texto en el centro
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
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .height(150.dp)
                            .background(Color(0xFF8bc6b0), shape = RoundedCornerShape(15.dp))
                            .clickable { /* Acción para Finanzas 1 */ }
                    ) {
                        Text(
                            text = "Septiembre",
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .height(150.dp)
                            .background(Color(0xFF94E1BE), shape = RoundedCornerShape(15.dp))
                            .clickable { /* Acción para Finanzas 2 */ }
                    ) {
                        Text(
                            text = "Octubre",
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            item {
                // Reservar área section
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center // Alinea el texto en el centro
                ) {
                    Text(
                        text = "Reservar área",
                        fontSize = 18.sp,
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
                    contentAlignment = Alignment.Center // Alinea el texto en el centro
                ) {
                    Text(
                        text = "Morosos",
                        fontSize = 18.sp,
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
                    onClick = { /* Acción para Morosos */ }
                )
            }
        }

        // Diálogo de Avisos
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        text = "Avisos",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Column {
                        // Campo de texto editable
                        TextField(
                            value = editableText,
                            onValueChange = { editableText = it },
                            label = { Text("Escribe aquí") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}*/

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
