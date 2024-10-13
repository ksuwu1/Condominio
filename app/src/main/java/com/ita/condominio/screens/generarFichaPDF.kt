package com.ita.condominio.screens

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

object PDFGenerator {
    fun generarFichaPDF(context: Context, bankName: String, accountNumber: String, referenceNumber: String, nombreCompleto: String, totalAmount: Double) {
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

        // Finalizar la página
        pdfDocument.finishPage(page)

        // Obtener el directorio "Documents" privado para la app
        val documentsDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

        // Verifica si el directorio no es nulo y existe o si puede ser creado
        documentsDir?.let {
            if (it.exists() || it.mkdirs()) {
                val fileName = "FichaPago_${System.currentTimeMillis()}.pdf"
                val filePath = File(it, fileName)

                try {
                    FileOutputStream(filePath).use { fileOutputStream ->
                        pdfDocument.writeTo(fileOutputStream)
                        Toast.makeText(context, "PDF generado en: ${filePath.absolutePath}", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Error al generar el PDF: ${e.message}", Toast.LENGTH_LONG).show()
                } finally {
                    pdfDocument.close() // Asegúrate de cerrar el documento PDF
                }
            } else {
                Toast.makeText(context, "Error: no se pudo crear el directorio de almacenamiento.", Toast.LENGTH_LONG).show()
            }
        } ?: run {
            // Si documentsDir es null, mostrar mensaje de error
            Toast.makeText(context, "Error al acceder al almacenamiento.", Toast.LENGTH_LONG).show()
        }
    }
}
