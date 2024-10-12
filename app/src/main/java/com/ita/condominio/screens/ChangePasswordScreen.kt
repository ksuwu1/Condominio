package com.ita.condominio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.CustomHeader

@Composable
fun ChangePasswordScreen(navController: NavHostController) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showAlert by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // CustomHeader fijo en la parte superior
        CustomHeader(title = "Cambiar contraseña")

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
                        .background(Color.White)
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
                        .background(Color.White)
                        .padding(8.dp)
                )
            }

            // Botón de Cambiar Contraseña
            item {
                Button(
                    onClick = {
                        if (newPassword == confirmPassword) {
                            // Aquí puedes agregar la lógica para cambiar la contraseña en la base de datos.
                            showAlert = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4DAD2))
                ) {
                    Text("Cambiar contraseña")
                }
            }
        }

        // BottomNavigationBar fijo en la parte inferior
        BottomNavigationBar(navController)
    }

    // Alert Dialog para mostrar la confirmación
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
}

@Preview(showBackground = true)
@Composable
fun PreviewChangePasswordScreen() {
    val navController = rememberNavController() // Crea un controlador de navegación simulado
    ChangePasswordScreen(navController)
}
