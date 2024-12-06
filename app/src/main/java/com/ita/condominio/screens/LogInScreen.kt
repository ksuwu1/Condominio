package com.ita.condominio.screens

import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ita.condominio.R
import com.ita.condominio.biometrics.BiometricPromptManager
import com.ita.condominio.database.DatabaseManager
import androidx.compose.material.ButtonDefaults

@Composable
fun LogInScreen(navController: NavController, activity: AppCompatActivity) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var useBiometrics by remember { mutableStateOf(true) } // Activado por defecto
    var loginError by remember { mutableStateOf<String?>(null) } // Mensaje de error de login

    val context = LocalContext.current
    val databaseManager = DatabaseManager(context)
    val promptManager by lazy { BiometricPromptManager(activity) }
    val biometricResult by promptManager.promptResults.collectAsState(initial = null)

    // Definición del launcher para iniciar la configuración de biometría
    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Puedes manejar la respuesta aquí si es necesario
    }

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Condominio",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 24.dp)
        )

        Text(
            text = "¡Bienvenido!",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Usar huella")
            Switch(
                checked = useBiometrics,
                onCheckedChange = { useBiometrics = it }
            )
        }

        Button(
            onClick = {
                if (databaseManager.isValidUser(email, password)) {
                    if (useBiometrics) {
                        promptManager.showBiometricPrompt(
                            title = "Autenticación requerida",
                            description = "Por favor, autentícate para continuar"
                        )
                    } else {
                        navController.navigate("MainMenu")
                    }
                } else {
                    loginError = "Correo o contraseña incorrectos"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF6A9C89),
                contentColor = Color.White
            )
        ) {
            Text(text = "Login", color = Color.White)
        }

        // Mostrar mensaje de error si es necesario
        loginError?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        biometricResult?.let { result ->
            Text(
                text = when (result) {
                    is BiometricPromptManager.BiometricResult.AuthenticationError -> result.error
                    BiometricPromptManager.BiometricResult.AuthenticationFailed -> "Autenticación fallida"
                    BiometricPromptManager.BiometricResult.FeatureUnavailable -> "Función no disponible"
                    BiometricPromptManager.BiometricResult.HardwareUnavailable -> "Hardware no disponible"
                    else -> ""
                },
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
