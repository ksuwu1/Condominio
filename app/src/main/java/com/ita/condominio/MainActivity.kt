package com.ita.condominio

import AccountDetailsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.ita.condominio.screens.LogoutScreen
import com.ita.condominio.screens.PaymentsScreen
import com.ita.condominio.screens.ReservationScreen
import com.ita.condominio.screens.VisitorsScreen
import com.ita.condominio.ui.theme.CondominioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CondominioTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AccountScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDDEEEA)),
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

        // Botones de opciones con tamaño ajustable
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

        // Botón Cerrar Sesión con ícono
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("logout") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent) // Cambiado a Color.Transparent
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

@Composable
fun AccountOptionButton(text: String, iconRes: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp) // Ajusta la altura del botón aquí
            .padding(horizontal = 20.dp, vertical = 8.dp), // Agrega margen a los lados
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFa9dfbf))
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(25.dp), // Cambia el tamaño del ícono si es necesario
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = Color.Black)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = Color(0xFFa9dfbf),
        contentColor = Color.Black
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.reporte),
                    contentDescription = "Informes",
                    modifier = Modifier.size(30.dp)
                )
            },
            selected = false,
            onClick = { /* Navegar a informes */ }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    modifier = Modifier.size(30.dp)
                )
            },
            selected = false,
            onClick = { /* Navegar a Home */ }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.chat),
                    contentDescription = "Chat",
                    modifier = Modifier.size(30.dp)
                )
            },
            selected = false,
            onClick = { /* Navegar a Chat */ }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "Usuario",
                    modifier = Modifier.size(30.dp)
                )
            },
            selected = false,
            onClick = { /* Navegar a Usuario */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    val navController = rememberNavController()
    AccountScreen(navController = navController)
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "account") {
        composable("account") { AccountScreen(navController) }
        composable("accountDetails") { AccountDetailsScreen(navController) }
        composable("visitors") { VisitorsScreen(navController) }
        composable("reservation") { ReservationScreen(navController) }
        composable("payments") { PaymentsScreen(navController) }
        composable("logout") { LogoutScreen(navController) }
    }
}
