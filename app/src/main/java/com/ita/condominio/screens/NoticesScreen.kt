package com.ita.condominio.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ita.condominio.BottomNavigationBar
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import com.ita.condominio.Network.AvisoViewModel
import com.ita.condominio.Network.ModelAvisos

@Composable
fun NoticesScreen(navController: NavHostController, viewModel: AvisoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val avisos by viewModel.avisos.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState()

    // Detectar orientación
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(key1 = true) {
        viewModel.fetchAvisos()
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                                tint = Color.White // Color de la flecha
                            )
                        }
                    }
                    Text(
                        text = "Condominio",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 50.dp),
                        color = Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(Color(0xFFC4D9D2)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Avisos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
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
                    LazyVerticalGrid(
                        columns = if (isLandscape) GridCells.Fixed(2) else GridCells.Fixed(1),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(avisos) { notice ->
                            NoticeCard(notice = notice) // Aquí pasamos el 'notice' individual
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun NoticeCard(notice: ModelAvisos) {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFC4DAD2),
            Color(0xFFE9EFEC)
        )
    )

    Box(
        modifier = Modifier.fillMaxWidth(), // Asegura que el Box ocupe todo el ancho
        contentAlignment = Alignment.Center // Centra el contenido dentro del Box
    ){
        Card(
            modifier = Modifier
                .width(400.dp)//mantiene un tamaño fijo de la publicacion
                .padding(16.dp)
                .shadow(4.dp, RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Column(
                modifier = Modifier
                    .background(brush = gradientBackground)
                    .padding(16.dp)
            ) {
                // Encabezado del aviso
                Text(
                    text = notice.tipo_aviso,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Cuerpo del aviso
                Text(
                    text = notice.titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = notice.fecha,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = notice.descripcion,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}

