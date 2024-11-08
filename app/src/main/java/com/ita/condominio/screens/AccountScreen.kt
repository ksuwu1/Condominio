package com.ita.condominio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ita.condominio.AccountOptionButton
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.R

@Composable
fun AccountScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Contenido desplazable en LazyColumn
        LazyColumn(
            modifier = Modifier
                .weight(1f) // Toma el espacio restante, para que el BottomNavigationBar se fije abajo
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Primera barra divisora con texto "Condominio"
            item {
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
            }

            // Segunda barra divisora con texto "Mi cuenta"
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(Color(0xFFC4D9D2)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Mi cuenta",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Botones de opciones
            val accountOptions = listOf(
                AccountOption("Cuenta", R.drawable.user, "accountDetails"),
                AccountOption("Visitantes", R.drawable.visitors, "visitors"),
                AccountOption("Reservación", R.drawable.reserv, "reservation"),
                AccountOption("Pagos", R.drawable.pay, "payments")
            )

            items(accountOptions) { option ->
                AccountOptionButton(
                    text = option.text,
                    iconRes = option.iconRes,
                    onClick = { navController.navigate(option.route) }
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Botón Cerrar Sesión
            item {
                Button(
                    onClick = { navController.navigate("login") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = "Cerrar sesión",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Cerrar sesión", color = Color.Black)
                }
            }
        }

        // Bottom Navigation Bar fijo en la parte inferior
        BottomNavigationBar(navController = navController)
    }
}
