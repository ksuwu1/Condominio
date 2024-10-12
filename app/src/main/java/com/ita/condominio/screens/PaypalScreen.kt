package com.ita.condominio.screens

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import com.paypal.android.sdk.payments.PaymentConfirmation
import java.math.BigDecimal

@Composable
fun PaypalScreen(total: Double) {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Pantalla de PayPal", fontSize = 24.sp)
        Text(text = "Total a pagar: \$$total", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { iniciarPago(total, paymentLauncher, activity) }) {
            Text("Pagar con PayPal")
        }
    }
}

private fun iniciarPago(total: Double, paymentLauncher: ActivityResultLauncher<Intent>, activity: Activity?) {
    val pago = PayPalPayment(
        BigDecimal.valueOf(total),
        "USD", // Cambia la moneda según sea necesario
        "Reserva en Condominio",
        PayPalPayment.PAYMENT_INTENT_SALE
    )

    val intent = Intent(activity, PaymentActivity::class.java).apply {
        putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, Config.PAYPAL_CONFIG)
        putExtra(PaymentActivity.EXTRA_PAYMENT, pago)
    }

    paymentLauncher.launch(intent)
}
