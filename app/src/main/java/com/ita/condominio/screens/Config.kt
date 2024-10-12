package com.ita.condominio.screens

import com.paypal.android.sdk.payments.PayPalConfiguration

object Config {
    const val PAYPAL_CLIENT_ID = "AdHxPz-pQGG1Gtb3O4lLzPLK7GOtATU5tmZXBrzb0cpjUkJl0aql26aufl2a4YMeydrtJvGKOkxAlPUM" // Reemplaza con tu Client ID de PayPal
    val PAYPAL_CONFIG = PayPalConfiguration()
        .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // Cambiar a ENVIRONMENT_PRODUCTION en producción
        .clientId(PAYPAL_CLIENT_ID)
}
