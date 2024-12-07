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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.ita.condominio.database.DatabaseManager
import com.ita.condominio.viewmodels.MorosoViewModel

@Composable
fun MorosoScreen(navController: NavHostController, viewModel: MorosoViewModel = viewModel()) {
    val morosos by viewModel.morosos.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(navController)

        LaunchedEffect(key1 = true) {
            viewModel.fetchMorosos()
        }

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = error ?: "Error desconocido", color = Color.Red)
                }
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(morosos) { moroso ->
                        MorosoCard(
                            casa = moroso.casa.toString(),
                            descripcion = moroso.descripcion,
                            detalleDescripcion = moroso.detalleDescripcion,
                            cantidad = moroso.cantidad
                        )
                    }
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
