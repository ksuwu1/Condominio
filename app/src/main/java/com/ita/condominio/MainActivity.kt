package com.ita.condominio

import AccountDetailsScreen
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ita.condominio.screens.AccountScreen
import com.ita.condominio.screens.BankPaymentScreen
import com.ita.condominio.screens.CardPaymentScreen
import com.ita.condominio.screens.ChangePasswordScreen
import com.ita.condominio.screens.Config
import com.ita.condominio.screens.LogInScreen
import com.ita.condominio.screens.LogoutScreen
import com.ita.condominio.screens.PaymentsScreen
import com.ita.condominio.screens.PaypalScreen
import com.ita.condominio.screens.ReservationScreen
import com.ita.condominio.screens.VisitorsScreen

import com.ita.condominio.ui.theme.CondominioTheme
import com.paypal.android.sdk.payments.PayPalService
import com.ita.condominio.screens.ReportsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Iniciar el servicio de PayPal
        val intent = Intent(this, PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, Config.PAYPAL_CONFIG)
        startService(intent)

        setContent {
            CondominioTheme {
                AppNavigation()
            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        // Detener el servicio de PayPal
        stopService(Intent(this, PayPalService::class.java))
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "account") {
        composable("MainMenu") { MainScreen(navController) }
        composable("account") { AccountScreen(navController) }
        composable("accountDetails") { AccountDetailsScreen(navController) }
        composable("visitors") { VisitorsScreen(navController) }
        composable("reservation") { ReservationScreen(navController) }
        composable("payments") { PaymentsScreen(navController) }
        composable("login") { LogInScreen(navController) }
        composable("logout") { LogoutScreen(navController) }
        composable("paypal/{total}") { backStackEntry ->
            val total = backStackEntry.arguments?.getString("total")?.toDouble() ?: 0.0
            PaypalScreen(total = total, navController = navController) // Pasamos el navController
        }
        composable("tarjeta") { CardPaymentScreen() } // Pantalla de pago con tarjeta
        composable("banco/{total}") { backStackEntry ->
            val total = backStackEntry.arguments?.getString("total")?.toDouble() ?: 0.0
            BankPaymentScreen(navController,total) // Pasamos el total
        }
        composable("ChangePassword") { ChangePasswordScreen(navController) }
        composable("reports") { ReportsScreen(navController) } // Añadido

    }
}