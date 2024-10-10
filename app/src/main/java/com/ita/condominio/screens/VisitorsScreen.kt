package com.ita.condominio.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import java.util.*

@Composable
fun VisitorsScreen(navController: NavHostController) {
    var visitors by remember { mutableStateOf(0) }
    var selectedDate by remember { mutableStateOf("") }
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Número de visitas:", style = MaterialTheme.typography.headlineMedium)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { if (visitors > 0) visitors-- }) {
                Text(text = "-")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = visitors.toString(), style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { visitors++ }) {
                Text(text = "+")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Date picker
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

        Button(onClick = { datePickerDialog.show() }) {
            Text(text = if (selectedDate.isEmpty()) "Seleccione la fecha" else "Fecha: $selectedDate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to generate QR code
        Button(onClick = {
            if (selectedDate.isNotEmpty()) {
                qrBitmap = generateQRCode("Visitantes: $visitors, Fecha: $selectedDate")
            } else {
                Toast.makeText(context, "Seleccione una fecha", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Guardar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display QR code if generated
        qrBitmap?.let {
            Image(bitmap = it.asImageBitmap(), contentDescription = "QR Code", modifier = Modifier.size(200.dp))
        }
    }
}

fun generateQRCode(text: String): Bitmap {
    val writer = QRCodeWriter()
    val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
    val width = bitMatrix.width
    val height = bitMatrix.height
    val bmp = createBitmap(width, height, Bitmap.Config.RGB_565)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bmp.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
        }
    }
    return bmp
}