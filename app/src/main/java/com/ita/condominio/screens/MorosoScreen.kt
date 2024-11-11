package com.ita.condominio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // Cambiamos imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ita.condominio.Models.ModelMorosos // Importamos el modelo

@Composable
fun MorosoScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // TopBar siempre al inicio, ocupa todo el ancho
        TopBar(navController)

        // Contenido desplazado hacia abajo para no tapar el TopBar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp), // Ajuste de padding
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Lista de morosos utilizando el modelo
            val morosos = listOf(
                ModelMorosos("Casa 1", "Enero 2023, Febrero 2023", "Mantenimiento", 150),
                ModelMorosos("Casa 2", "Marzo 2023", "Reparaciones", 100),
                ModelMorosos("Casa 3", "Abril 2023, Mayo 2023", "Servicios", 200),
                ModelMorosos("Casa 4", "Junio 2023", "Mantenimiento", 250),
                ModelMorosos("Casa 5", "Julio 2023, Agosto 2023", "Luz", 300),
                ModelMorosos("Casa 6", "Septiembre 2023", "Mantenimiento", 400),
                ModelMorosos("Casa 7", "Octubre 2023", "Reparaciones", 350),
                ModelMorosos("Casa 8", "Noviembre 2023", "Servicios", 220),
                ModelMorosos("Casa 9", "Diciembre 2023", "Mantenimiento", 280),
                ModelMorosos("Casa 10", "Enero 2024", "Otros Adeudos", 450)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(morosos) { moroso ->
                    MorosoCard(
                        casa = moroso.casa,
                        descripcion = moroso.descripcion,
                        detalleDescripcion = moroso.detalleDescripcion,
                        cantidad = moroso.cantidad
                    )
                }
            }
        }
    }
}

@Composable
fun TopBar(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFF699C89)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                androidx.compose.material.Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color.White
                )
            }
            Text(
                text = "Pendientes de pago",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp) // Ajuste para separación de la flecha
            )
        }
    }
}

@Composable
fun MorosoCard(casa: String, descripcion: String, detalleDescripcion: String, cantidad: Int) {
    Card(
        elevation = 6.dp,
        backgroundColor = Color(0xFFE0F2F1),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = casa,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Text(
                    text = descripcion,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = detalleDescripcion,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "\$${cantidad}.00",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
