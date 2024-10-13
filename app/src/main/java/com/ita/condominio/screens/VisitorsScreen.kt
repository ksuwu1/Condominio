package com.ita.condominio.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.CustomHeader
import java.util.*
import com.ita.condominio.R

@Composable
fun VisitorsScreen(navController: NavHostController) {
    var visitors by remember { mutableStateOf(0) }
    var selectedDate by remember { mutableStateOf("") }
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    // Estado para controlar el diálogo de alerta
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Encabezado personalizado
        CustomHeader(title = "Registro de Visitas")

        // Contenido desplazable con LazyColumn
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Columna para el icono y el texto
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth() // Asegura que ocupe todo el ancho
                ) {
                    // Icono encima del texto
                    Icon(
                        painter = painterResource(id = R.drawable.visitors), // Cambia a tu icono deseado
                        contentDescription = "Número de visitantes",
                        modifier = Modifier.size(48.dp) // Ajusta el tamaño del icono según sea necesario
                    )

                    // Texto centrado
                    Text(
                        text = "Ingrese el número de visitantes:",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(top = 8.dp) // Espaciado entre el icono y el texto
                    )
                }

                // Resto del contenido...
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { if (visitors > 0) visitors-- }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4DAD2))) {
                        Text(text = "-")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = visitors.toString(), style = MaterialTheme.typography.headlineLarge)
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = { visitors++ }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4DAD2))) {
                        Text(text = "+")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Selector de fecha
                val calendar = Calendar.getInstance()
                val datePickerDialog = DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        selectedDate = "$dayOfMonth/${month + 1}/$year"
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )

                Button(onClick = { datePickerDialog.show() }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4DAD2))) {
                    Text(text = if (selectedDate.isEmpty()) "Seleccione la fecha" else "Fecha: $selectedDate")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para generar código QR
                Button(onClick = {
                    if (selectedDate.isNotEmpty()) {
                        qrBitmap = generateQRCode("Visitantes: $visitors, Fecha: $selectedDate")
                        showDialog = true // Mostrar el diálogo después de generar el QR
                    } else {
                        Toast.makeText(context, "Seleccione una fecha", Toast.LENGTH_SHORT).show()
                    }
                }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4DAD2))) {
                    Text(text = "Guardar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar código QR si se generó
                qrBitmap?.let {
                    Image(bitmap = it.asImageBitmap(), contentDescription = "QR Code", modifier = Modifier.size(200.dp))
                }
            }
        }

        // Mostrar el diálogo de alerta
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Código QR Generado") },
                text = { Text("Se ha generado el QR de visita. Pídele a tus visitas que muestren el QR en la caseta de vigilancia para obtener acceso al condominio.") },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Aceptar")
                    }
                }
            )
        }

        // Barra de navegación inferior
        BottomNavigationBar(navController)
    }
}

fun generateQRCode(text: String): Bitmap {
    val writer = QRCodeWriter()
    val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
    val width = bitMatrix.width
    val height = bitMatrix.height
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bmp.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
        }
    }
    return bmp
}
