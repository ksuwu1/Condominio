package com.ita.condominio.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.ita.condominio.R


@Composable
fun LoginScreen(navController: NavHostController) {
    // Estado para almacenar los valores de los campos de texto
    var houseNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Diseño de la pantalla
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título "Login"
        Text(
            text = "Login",
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .padding(bottom = 24.dp)
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
            visualTransformation = PasswordVisualTransformation(), // Oculta la contraseña
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // Botón "Login" centrado
        Button(
            onClick = { /* Accion de login */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.verde_medio), // Color del boton
            )
        ) {
            Text(
                text = "Login",
                color = Color.White
            )
        }
    }
}

// Vista previa de la pantalla
/*@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}*/