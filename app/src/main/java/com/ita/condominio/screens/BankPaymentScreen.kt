package com.ita.condominio.screens

import android.content.ContentValues
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import java.io.OutputStream

@Composable
fun BankPaymentScreen(navController: NavHostController, totalAmount: Double) {
    val bankName = "HSBC"
    val accountNumber = "8765-4321-1098-7654"
    val referenceNumber = "REF-002-2024"
    val instructions =
        "Por favor, realice el pago en Cajero o Ventanilla HSBC utilizando el número de referencia y envíe el comprobante al siguiente número de WhatsApp: 2226061577."

    var nombreCompleto by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pago Referencia Bancaria",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Banco: $bankName", fontSize = 18.sp)
        Text(text = "Número de cuenta: $accountNumber", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = referenceNumber,
            onValueChange = {},
            label = { Text("Referencia bancaria") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            readOnly = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombreCompleto,
            onValueChange = { nombreCompleto = it },
            label = { Text("Nombre completo del pagador") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Cantidad a pagar: \$${"%.2f".format(totalAmount)}", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = instructions,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                generarFichaPDF(navController.context, bankName, accountNumber, referenceNumber, nombreCompleto, totalAmount)
            }
        ) {
            Text(text = "Generar ficha de pago")
        }
    }
}

fun generarFichaPDF(
    context: Context,
    bankName: String,
    accountNumber: String,
    referenceNumber: String,
    nombreCompleto: String,
    totalAmount: Double
) {
    // Crear el PDF
    val pdfDocument = PdfDocument()
    val paint = Paint()

    // Crear una página del PDF
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas: Canvas = page.canvas

    // Estilo del título
    paint.textSize = 24f
    paint.isFakeBoldText = true
    canvas.drawText("Ficha de Pago Bancario", 150f, 50f, paint)

    // Estilo del contenido
    paint.textSize = 16f
    paint.isFakeBoldText = false
    canvas.drawText("Banco: $bankName", 50f, 100f, paint)
    canvas.drawText("Número de cuenta: $accountNumber", 50f, 130f, paint)
    canvas.drawText("Referencia: $referenceNumber", 50f, 160f, paint)
    canvas.drawText("Nombre del pagador: $nombreCompleto", 50f, 190f, paint)
    canvas.drawText("Cantidad a pagar: \$${"%.2f".format(totalAmount)}", 50f, 220f, paint)

    pdfDocument.finishPage(page)

    // Usar MediaStore para guardar el archivo en la carpeta "Documents"
    val fileName = "FichaPago_${System.currentTimeMillis()}.pdf"
    val resolver = context.contentResolver

    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
    }

    val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

    uri?.let {
        try {
            val outputStream: OutputStream? = resolver.openOutputStream(it)

            outputStream?.use { stream ->
                pdfDocument.writeTo(stream)
                Toast.makeText(context, "PDF guardado en Documents", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error al guardar el PDF: ${e.message}", Toast.LENGTH_LONG).show()
        } finally {
            pdfDocument.close()
        }
    } ?: run {
        Toast.makeText(context, "Error al acceder a MediaStore", Toast.LENGTH_LONG).show()
    }
}
