package com.ita.condominio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.R

@Composable
fun MorosoScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Barra divisora con texto "Condominio"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xFF699C89)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CONDOMINIO",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp),
                color = Color.White
            )
        }

        // Barra divisora con texto "Morosos"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color(0xFFC4D9D2)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "MOROSOS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }

        // Flecha para regresar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color.Black // Color de la flecha
                )
            }
        }

        // Datos de morosos y adeudos
        val morosos = listOf(
            Pair("Casa 1", Pair("Enero 2023, Febrero 2023", 150)),
            Pair("Casa 2", Pair("Marzo 2023", 100)),
            Pair("Casa 3", Pair("Abril 2023, Mayo 2023", 200)),
            Pair("Casa 4", Pair("Junio 2023", 250)),
            Pair("Casa 5", Pair("Julio 2023, Agosto 2023", 300))
        )

        val adeudos = listOf(
            Triple("Casa 1", "Cuota de mantenimiento", 50),
            Triple("Casa 2", "Reparaciones", 150),
            Triple("Casa 3", "Servicios", 75),
            Triple("Casa 4", "Mantenimiento de jardín", 100),
            Triple("Casa 5", "Luz", 200)
        )

        Column(modifier = Modifier.padding(16.dp)) {
            // Tabla de Morosos
            Text(
                text = "Morosos:",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally) // Centra el texto
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Encabezados de la tabla
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "No. Casa", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(end = 16.dp), color = Color.Black)
                Text(text = "Meses Adeudados", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(end = 16.dp), color = Color.Black)
                Text(text = "Cantidad", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), color = Color.Black)
            }

            Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            LazyColumn {
                items(morosos) { (casa, adeudos) ->
                    Row(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                        Text(text = casa, modifier = Modifier.weight(1f).padding(end = 16.dp), color = Color.Black) // Espacio entre columnas
                        Text(text = "${adeudos.first}", modifier = Modifier.weight(1f).padding(end = 16.dp), color = Color.Black) // Sin "Meses: " y más espaciado
                        Text(text = "\$${adeudos.second}", modifier = Modifier.weight(1f), color = Color.Black) // Cambiado a color negro
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tabla de Adeudos
            Text(
                text = "Adeudos:",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally) // Centra el texto
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Encabezados de la tabla
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "No. Casa", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(end = 16.dp), color = Color.Black)
                Text(text = "Descripción", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(end = 16.dp), color = Color.Black)
                Text(text = "Cantidad", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), color = Color.Black)
            }

            Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            LazyColumn {
                items(adeudos) { (casa, descripcion, cantidad) ->
                    Row(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                        Text(text = casa, modifier = Modifier.weight(1f).padding(end = 16.dp), color = Color.Black) // Cambiado a color negro
                        Text(text = descripcion, modifier = Modifier.weight(1f).padding(end = 16.dp), color = Color.Black) // Cambiado a color negro
                        Text(text = "\$${cantidad}", modifier = Modifier.weight(1f), color = Color.Black) // Cambiado a color negro
                    }
                }
            }
        }

    }
}

