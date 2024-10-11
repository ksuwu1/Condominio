package com.ita.condominio

import AccountDetailsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ita.condominio.screens.AccountScreen
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


