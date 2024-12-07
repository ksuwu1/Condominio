package com.ita.condominio

import AccountDetailsScreen
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ita.condominio.database.DatabaseHelper
import com.ita.condominio.database.DatabaseManager
import com.ita.condominio.screens.*
import com.ita.condominio.ui.theme.CondominioTheme
import com.paypal.android.sdk.payments.PayPalService

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var databaseManager: DatabaseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Iniciar el servicio de PayPal
        val intent = Intent(this, PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, Config.PAYPAL_CONFIG)
        startService(intent)

        // Instanciar DatabaseHelper y DatabaseManager
        dbHelper = DatabaseHelper(this)
        databaseManager = DatabaseManager(this)

        // Asegúrate de abrir la base de datos en modo escritura si necesitas hacerlo aquí
        dbHelper.writableDatabase

        setContent {
            CondominioTheme {
                ComposeApp(activity = this, databaseManager = databaseManager)
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
fun ComposeApp(activity: AppCompatActivity, databaseManager: DatabaseManager) {
    val navController = rememberNavController()
    Surface(color = Color.White) {
        AppNavigation(navController = navController, activity = activity, databaseManager = databaseManager)
    }
}

@Composable
fun AppNavigation(navController: NavHostController, activity: AppCompatActivity, databaseManager: DatabaseManager) {
    NavHost(navController = navController, startDestination = "login") {
        composable("MainMenu") { MainScreen(navController) }
        composable("notices") { NoticesScreen(navController) }
        composable("account") { AccountScreen(navController) }

        composable("accountDetails/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            if (userId != null) {
                AccountDetailsScreen(navController, userId.toString())
            } else {
                Log.e("AppNavigation", "User ID is null")
                // Opcional: navegar a una pantalla de error o mostrar un mensaje de error.
            }
        }

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
