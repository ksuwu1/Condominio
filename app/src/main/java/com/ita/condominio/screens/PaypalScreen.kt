package com.ita.condominio.screens

import android.app.Activity
import android.content.Intent
import android.util.Log

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.CustomHeader
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import com.paypal.android.sdk.payments.PaymentConfirmation
import java.math.BigDecimal
import com.ita.condominio.R

@Composable
fun PaypalScreen(total: Double, navController: NavHostController) {
    val context = LocalContext.current
    val activity = context as? Activity

    // Configura el ActivityResultLauncher para manejar el resultado del pago
    val paymentLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val confirmation = result.data?.getParcelableExtra<PaymentConfirmation>(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
            confirmation?.let {
                // Maneja la confirmación de pago aquí
                Log.i("PayPal", "Payment Successful: ${it.toJSONObject().toString(4)}")
            }
        } else {
            Log.e("PayPal", "Payment failed or cancelled.")
        }
    }

    // Iniciar el servicio de PayPal
    val intentService = Intent(context, PayPalService::class.java).apply {
        putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, Config.PAYPAL_CONFIG)
    }
    context.startService(intentService)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header personalizable
        CustomHeader(title = "Pago en línea PayPal")

        // Contenido desplazable con LazyColumn
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Icono encima del texto "PayPal"
                Icon(
                    painter = painterResource(id = R.drawable.paypal1), // Cambia a tu icono de PayPal
                    contentDescription = "PayPal Icon",
                    modifier = Modifier.size(48.dp).padding(bottom = 8.dp)
                )
                Text(text = "PayPal", fontSize = 24.sp, modifier = Modifier.padding(bottom = 8.dp))
                Text(text = "Total a pagar: \$$total", fontSize = 20.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { iniciarPago(total, paymentLauncher, activity) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color(0xFFC4DAD2)) // verde_claro
                ) {
                    Text("Pagar con PayPal", color = Color.White) // Set button text color to white for contrast
                }
            }
        }

        BottomNavigationBar(navController) // Ahora se pasa el navController correctamente
    }
}


private fun iniciarPago(total: Double, paymentLauncher: ActivityResultLauncher<Intent>, activity: Activity?) {
    val pago = PayPalPayment(
        BigDecimal.valueOf(total),
        "USD",
        "Reserva en Condominio",
        PayPalPayment.PAYMENT_INTENT_SALE
    )

    val intent = Intent(activity, PaymentActivity::class.java).apply {
        putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, Config.PAYPAL_CONFIG)
        putExtra(PaymentActivity.EXTRA_PAYMENT, pago)
    }

    paymentLauncher.launch(intent)
}
