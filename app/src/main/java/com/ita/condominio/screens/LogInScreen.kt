package com.ita.condominio.screens

import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ita.condominio.biometrics.BiometricPromptManager
import androidx.compose.material3.Button

@Composable
fun LogInScreen(navController: NavHostController, activity: AppCompatActivity) {
    // Estados para campos de texto y configuración de autenticación biométrica
    var houseNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var useBiometrics by remember { mutableStateOf(true) } // Activado por defecto

    // Inicializar BiometricPromptManager
    val promptManager by lazy { BiometricPromptManager(activity) }
    val biometricResult by promptManager.promptResults.collectAsState(initial = null)

    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { /* Handle result if needed */ }
    )

    // Manejar cambios en el resultado de la autenticación biométrica
    LaunchedEffect(biometricResult) {
        when (biometricResult) {
            is BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                // Si la autenticación es exitosa, navegar a MainMenu
                navController.navigate("MainMenu")
            }
            is BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                // Si la autenticación biométrica no está configurada, abrir la configuración de seguridad
                if (Build.VERSION.SDK_INT >= 30) {
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                        )
                    }
                    enrollLauncher.launch(enrollIntent)
                }
            }
            else -> Unit
        }
    }

    // Diseño de la pantalla
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Campo de texto para "No. Casa"
        OutlinedTextField(
            value = houseNumber,
            onValueChange = { houseNumber = it },
            label = { Text("No. Casa") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Campo de texto para "Password"
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // Switch para activar/desactivar autenticación biométrica
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Usar huella")
            Switch(
                checked = useBiometrics,
                onCheckedChange = { useBiometrics = it },
                //colors = SwitchDefaults.colors(checkedThumbColor = colorResource(R.color.verde_medio))
            )
        }

        // Botón "Login"
        Button(
            onClick = {
                if (useBiometrics) {
                    // Mostrar autenticación biométrica
                    promptManager.showBiometricPrompt(
                        title = "Autenticación requerida",
                        description = "Por favor, autentícate para continuar"
                    )
                } else {
                    // Si la autenticación biométrica está desactivada, proceder a MainMenu
                    navController.navigate("MainMenu")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6A9C89),
                contentColor = Color.White
            )
        ) {
            Text(text = "Login", color = Color.White)
        }

        // Mensaje de error biométrico, si lo hay
        biometricResult?.let { result ->
            Text(
                text = when (result) {
                    is BiometricPromptManager.BiometricResult.AuthenticationError -> result.error
                    BiometricPromptManager.BiometricResult.AuthenticationFailed -> "Authentication failed"
                    BiometricPromptManager.BiometricResult.FeatureUnavailable -> "Feature unavailable"
                    BiometricPromptManager.BiometricResult.HardwareUnavailable -> "Hardware unavailable"
                    else -> ""
                },
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


