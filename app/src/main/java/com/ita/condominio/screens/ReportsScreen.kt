package com.ita.condominio.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ita.condominio.CustomHeader
import com.ita.condominio.BottomNavigationBar

@Composable
fun ReportsScreen(navController: NavHostController) {
    var message by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<String>() }
    var successMessage by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Lanzador para la selección de imágenes
    val getImage: ActivityResultLauncher<Intent> = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    selectedImageUri = uri
                }
            }
        }
    )

    Column(modifier = Modifier.fillMaxSize()) {
        CustomHeader(title = "Reportes")

        selectedImageUri?.let {
            Text(text = "Imagen seleccionada: $it")
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(messages) { msg ->
                Text(text = msg, modifier = Modifier.padding(4.dp))
            }
        }

        if (successMessage.isNotEmpty()) {
            Text(
                text = successMessage,
                modifier = Modifier.padding(8.dp),
                color = Color.Green
            )
        }

        Row(modifier = Modifier.padding(8.dp)) {
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Escribe un mensaje") },
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFC4D9D2)), // Aplicar el color de fondo
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send
                )
            )

            IconButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    getImage.launch(intent)
                }
            ) {
                Icon(Icons.Filled.Camera, contentDescription = "Seleccionar imagen", tint = Color.Black)
            }

            Button(
                onClick = {
                    if (message.isNotEmpty()) {
                        messages.add(message)
                        successMessage = "Mensaje enviado con éxito"
                        message = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4D9D2))
            ) {
                Text("Enviar", color = Color.Black)
            }
        }

        BottomNavigationBar(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun ReportsScreenPreview() {
    ReportsScreen(navController = rememberNavController())
}
