package com.ita.condominio.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // Importar Color para usar colores hexadecimales
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.CustomHeader

@Composable
fun ChatScreen(navController: NavHostController) {
    var message by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.fillMaxSize()) {
        // Encabezado personalizado
        CustomHeader(title = "Chat")

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(messages) { msg ->
                Text(text = msg, modifier = Modifier.padding(4.dp))
            }
        }

        Row(modifier = Modifier.padding(8.dp)) {
            TextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Escribe un mensaje") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send // Establece la acción del teclado en "Enviar"
                )
            )
            Button(
                onClick = {
                    if (message.isNotEmpty()) {
                        messages.add(message)
                        message = ""
                        // Aquí puedes agregar código para enviar el mensaje a la base de datos
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4D9D2)) // Cambia el color del botón
            ) {
                Text("Enviar", color = Color.Black) // Cambia el color del texto a negro
            }
        }

        BottomNavigationBar(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    // Necesitas un NavHostController simulado para la previsualización.
    // Puedes crear un MockNavController si es necesario.
    ChatScreen(navController = mockNavController()) // Utiliza un controlador de navegación simulado.
}

// Mock para el controlador de navegación (si es necesario)
@Composable
fun mockNavController(): NavHostController {
    // Implementa un controlador de navegación simulado según sea necesario para la previsualización.
    return rememberNavController()
}
