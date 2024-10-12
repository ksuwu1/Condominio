package com.ita.condominio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.ita.condominio.ui.theme.CondominioTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import com.ita.condominio.screens.AccountDetailsScreen
import com.ita.condominio.screens.LogoutScreen
import com.ita.condominio.screens.PaymentsScreen
import com.ita.condominio.screens.ReservationScreen
import com.ita.condominio.screens.VisitorsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CondominioTheme {
                // Llamamos a la función de navegación
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
            .padding(16.dp)
            .background(Color(0xFFDDEEEA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mi cuenta",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Botón Cuenta
        AccountOptionButton(
            text = "Cuenta",
            iconRes = R.drawable.user, // Reemplaza con tu ícono de cuenta
            onClick = { navController.navigate("accountDetails") }  // Navega a la pantalla de cuenta
        )

        // Botón Visitantes
        AccountOptionButton(
            text = "Visitantes",
            iconRes = R.drawable.visitors, // Reemplaza con tu ícono de visitantes
            onClick = { navController.navigate("visitors") }  // Navega a la pantalla de visitantes
        )

        // Botón Reservación
        AccountOptionButton(
            text = "Reservación",
            iconRes = R.drawable.reserv, // Reemplaza con tu ícono de reservación
            onClick = { navController.navigate("reservation") }  // Navega a la pantalla de reservación
        )

        // Botón Pagos
        AccountOptionButton(
            text = "Pagos",
            iconRes = R.drawable.pay, // Reemplaza con tu ícono de pagos
            onClick = { navController.navigate("payments") }  // Navega a la pantalla de pagos
        )

        // Botón Cerrar Sesión
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Cerrar sesión",
            color = Color.Black,
            modifier = Modifier.clickable { navController.navigate("logout") }  // Navega a la pantalla de logout
        )
    }
}

@Composable
fun AccountOptionButton(text: String, iconRes: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9AB4A3))
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = Color.Black)
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