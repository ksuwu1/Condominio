package com.ita.condominio.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.R
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.platform.LocalConfiguration

// Clase de datos para estructurar un aviso
data class Notice(
    val title: String,
    val notice: String,
    val date: String,
    val bodytext: String
)

@Composable
fun NoticeCard(notice: Notice) {
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
                    text = notice.title,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Cuerpo del aviso
                Text(
                    text = notice.notice,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = notice.date,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = notice.bodytext,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun NoticesScreen(navController: NavHostController) {
    // Lista de avisos
    val notices = listOf(
        Notice(
            title = "Aviso para la calle ####",
            notice = "Ejemplo de aviso 1",
            date = "31 de octubre del 2024",
            bodytext = "Se informa a los habitantes de la calle ####, que habra una fiesta de Halloween en la casa ##, La cual se acabara a las 12 am del dia siguiente"
        ),
        Notice(
            title = "Aviso general",
            notice = "Ejemplo de aviso 2",
            date = "3 de noviembre del 2024",
            bodytext = "La piscina entrara en mantenimiento debido a fallas constantes en los filtros. Se espera que las reparaciones no pasen de dos semanas"
        ),
        Notice(
            title = "Aviso general",
            notice = "Ejemplo de aviso 3",
            date = "20 de noviembre del 2024",
            bodytext = "Ya no se que inventarme para el aviso, ayuda esto es encerio. Pero necesito rellenar el espacio. espero esto sea suficiente."
        ),
        Notice(
            title = "Aviso ola",
            notice = "Ejemplo de aviso 4",
            date = "20 de noviembre del 2024",
            bodytext = "Si, dice lo mismo que el anterior. Ya no se que inventarme para el aviso, ayuda esto es encerio. Pero necesito rellenar el espacio. espero esto sea suficiente."
        )
    )

    // Detectar orientación
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Banner en la parte superior
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
                    // Flecha para regresar
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

            // Lista de avisos individuales
            LazyVerticalGrid(
                columns = if (isLandscape) GridCells.Fixed(2) else GridCells.Fixed(1),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(notices) { notice ->
                    NoticeCard(notice = notice)
                }
            }
        }
    }
}
