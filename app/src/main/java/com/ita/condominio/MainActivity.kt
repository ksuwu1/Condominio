package com.ita.condominio

import AccountDetailsScreen
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ita.condominio.screens.*

import com.ita.condominio.ui.theme.CondominioTheme
import com.paypal.android.sdk.payments.PayPalService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Iniciar el servicio de PayPal
        val intent = Intent(this, PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, Config.PAYPAL_CONFIG)
        startService(intent)

        setContent {
            CondominioTheme {
                ComposeApp(this)
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
fun ComposeApp(activity: AppCompatActivity) {
    val navController = rememberNavController()
    Surface(color = Color.White) {
        AppNavigation(navController = navController, activity = activity)
    }
}

@Composable
fun AppNavigation(navController: NavHostController, activity: AppCompatActivity) {
    NavHost(navController = navController, startDestination = "login") {
        composable("MainMenu") { MainScreen(navController) }
        composable("notices") { NoticesScreen(navController) }
        composable("account") { AccountScreen(navController) }
        composable("accountDetails") { AccountDetailsScreen(navController) }
        composable("visitors") { VisitorsScreen(navController) }
        composable("reservation") { ReservationScreen(navController) }
        composable("payments") { PaymentsScreen(navController) }
        composable("login") { LogInScreen(navController, activity) }
        composable("logout") { LogoutScreen(navController) }
        composable("paypal/{total}") { backStackEntry ->
            val total = backStackEntry.arguments?.getString("total")?.toDouble() ?: 0.0
            PaypalScreen(total = total, navController = navController)
        }
        composable("tarjeta") { CardPaymentScreen() }
        composable("banco/{total}") { backStackEntry ->
            val total = backStackEntry.arguments?.getString("total")?.toDouble() ?: 0.0
            BankPaymentScreen(navController, total)
        }
        composable("ChangePassword") { ChangePasswordScreen(navController) }
        composable("reports") { ReportsScreen(navController) }
        composable("morosos") { MorosoScreen(navController) }
        composable("informe") { InformeScreen(navController) }
    }
}
