package com.ita.condominio.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.ita.condominio.CustomHeader2 // Aquí importamos CustomHeader2
import com.ita.condominio.Models.VisitRecord
import com.ita.condominio.R
import java.util.*

@Composable
fun VisitorsScreen(navController: NavHostController) {
    var visitors by remember { mutableStateOf(0) }
    var selectedDate by remember { mutableStateOf("") }
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var visitHistory by remember { mutableStateOf(listOf<VisitRecord>()) }
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Usamos CustomHeader2 en lugar de CustomHeader
        CustomHeader2(navController = navController, title = "Registro de Visitas")

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.visitors),
                        contentDescription = "Número de visitantes",
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "Ingrese el número de visitantes:",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

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

                Button(onClick = {
                    if (selectedDate.isNotEmpty()) {
                        val newQRCode = generateQRCode("Visitantes: $visitors, Fecha: $selectedDate")
                        qrBitmap = newQRCode
                        visitHistory = visitHistory + VisitRecord(visitors, selectedDate, newQRCode)
                        showDialog = true
                    } else {
                        Toast.makeText(context, "Seleccione una fecha", Toast.LENGTH_SHORT).show()
                    }
                }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4DAD2))) {
                    Text(text = "Guardar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                qrBitmap?.let {
                    Image(bitmap = it.asImageBitmap(), contentDescription = "QR Code", modifier = Modifier.size(200.dp))
                }
            }

            // Agregar un divisor visual entre el formulario y el historial
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Divider(color = Color.Gray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Historial de Visitas", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Mostrar el historial de visitas registradas
            items(visitHistory) { record ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Visitantes: ${record.visitors}",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = "Fecha: ${record.date}",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Image(
                            bitmap = record.qrCode.asImageBitmap(),
                            contentDescription = "QR Code",
                            modifier = Modifier
                                .size(150.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                visitHistory = visitHistory.filter { it != record }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCDD2))
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }

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
