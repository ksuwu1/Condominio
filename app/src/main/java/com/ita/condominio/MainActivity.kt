package com.ita.condominio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import com.ita.condominio.screens.AccountDetailsScreen
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
            .padding(16.dp)
            .background(Color(0xFFF0F4F8)),  // Color más claro y moderno de fondo
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título principal
        Text(
            text = "Mi cuenta",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp),
            color = Color(0xFF1A1A1A)
        )

        // Botones con íconos
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

        // Botón de cerrar sesión al final
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Cerrar sesión",
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = Color(0xFFDA1E28),
            modifier = Modifier
                .clickable { navController.navigate("logout") }
                .padding(vertical = 8.dp)
        )
    }
}

@Composable
fun AccountOptionButton(text: String, iconRes: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(56.dp),  // Altura más alta para los botones
        shape = RoundedCornerShape(12.dp),  // Bordes redondeados
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))  // Color más fuerte para el fondo
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(28.dp),  // Íconos un poco más grandes
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(16.dp))  // Espacio entre ícono y texto
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp  // Texto más grande
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
