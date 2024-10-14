package com.ita.condominio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.ita.condominio.AppNavigation
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.R


@Composable
fun AccountScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
            //.background(Color(0xFFDDEEEA)),
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

        // Segunda barra divisora con texto "Mi cuenta"
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

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de opciones
        AccountOptionButton(
            text = "Cuenta",
            iconRes = R.drawable.user,
            onClick = { navController.navigate("accountDetails") }
        )
        AccountOptionButton(
            text = "Visitantes",
            iconRes = R.drawable.visitors,
            onClick = { navController.navigate("visitors") }
        )
        AccountOptionButton(
            text = "Reservación",
            iconRes = R.drawable.reserv,
            onClick = { navController.navigate("reservation") }
        )
        AccountOptionButton(
            text = "Pagos",
            iconRes = R.drawable.pay,
            onClick = { navController.navigate("payments") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* Botón Iniciar Sesión
        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.login),
                contentDescription = "Iniciar sesión",
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Iniciar sesión", color = Color.Black)
        }*/

        // Botón Cerrar Sesión
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

        Spacer(modifier = Modifier.weight(1f))

        // Bottom Navigation
        BottomNavigationBar(navController)
    }
}

