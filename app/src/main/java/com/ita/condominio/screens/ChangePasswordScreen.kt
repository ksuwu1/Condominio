package com.ita.condominio.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.CustomHeader
import com.ita.condominio.CustomHeader2
import com.ita.condominio.Network.AccountDetailsViewModel
import com.ita.condominio.Network.ChangePasswordRequest
import com.ita.condominio.Network.RetrofitInstance

import kotlinx.coroutines.launch

@Composable
fun ChangePasswordScreen(navController: NavHostController, viewModel: AccountDetailsViewModel = viewModel()) {
    val usuario by viewModel.usuario.observeAsState()

    // Usar valores directamente desde el usuario
    val id_user = usuario?.id_usuario ?: 0
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var currentPassword by remember { mutableStateOf("") }
    var showAlert by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // CustomHeader fijo en la parte superior
        CustomHeader2(navController = navController, title = "Cambiar contraseña")

        // Contenido desplazable con LazyColumn
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Contraseña Actual",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                BasicTextField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(8.dp)
                )
            }

            item {
                Text(
                    text = "Nueva Contraseña",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                BasicTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(8.dp)
                )
            }

            item {
                Text(
                    text = "Confirmar Contraseña",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                BasicTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(8.dp)
                )
            }

            // Botón de Cambiar Contraseña
            item {
                Button(
                    onClick = {
                        if (newPassword == confirmPassword) {
                            coroutineScope.launch {
                                isLoading = true
                                val success = changePassword(currentPassword, newPassword, id_user)
                                isLoading = false
                                if (success) {
                                    showAlert = true
                                } else {
                                    showError = true
                                }
                            }
                        } else {
                            showError = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4DAD2)),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                    } else {
                        Text("Cambiar contraseña")
                    }
                }
            }
        }

        // BottomNavigationBar fijo en la parte inferior
        BottomNavigationBar(navController)
    }

    // Alert Dialog para mostrar éxito
    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            title = { Text("Éxito") },
            text = { Text("La contraseña ha sido cambiada exitosamente.") },
            confirmButton = {
                Button(onClick = { showAlert = false }) {
                    Text("Aceptar")
                }
            }
        )
    }

    // Alert Dialog para mostrar error
    if (showError) {
        AlertDialog(
            onDismissRequest = { showError = false },
            title = { Text("Error") },
            text = { Text("No se pudo cambiar la contraseña. Por favor, verifica los datos ingresados.") },
            confirmButton = {
                Button(onClick = { showError = false }) {
                    Text("Aceptar")
                }
            }
        )
    }
}

suspend fun changePassword(currentPassword: String, newPassword: String, id_user: Int): Boolean {
    return try {
        val response = RetrofitInstance.api.changePassword(
            userId = id_user, // ID del usuario (ajustar según la lógica de tu app)
            passwordRequest = ChangePasswordRequest(
                currentPassword = currentPassword,
                newPassword = newPassword
            )
        )
        response.isSuccessful
    } catch (e: Exception) {
        false
    }
}

@Preview(showBackground = true)
@Composable
fun ChangePasswordScreenPreview() {
    ChangePasswordScreen(navController = rememberNavController())
}