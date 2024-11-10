package com.ita.condominio.screens

import com.ita.condominio.R
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.CustomHeader
import java.util.*
import android.content.Intent
import android.provider.CalendarContract
import android.app.AlertDialog

@Composable
fun ReservationScreen(navController: NavHostController) {
    // Estado de las variables
    var fecha by remember { mutableStateOf("") }
    var horaInicio by remember { mutableStateOf("") }
    var horaCierre by remember { mutableStateOf("") }
    var visitantes by remember { mutableStateOf("") }
    var bañosChecked by remember { mutableStateOf(false) }
    var palapaChecked by remember { mutableStateOf(false) }
    var salonChecked by remember { mutableStateOf(false) }
    var albercaChecked by remember { mutableStateOf(false) }

    // Precios de los espacios
    val precioBaños = 100
    val precioPalapa = 500
    val precioSalon = 1500
    val precioAlberca = 1000

    // Calcular el total
    val total = (if (bañosChecked) precioBaños else 0) +
            (if (palapaChecked) precioPalapa else 0) +
            (if (salonChecked) precioSalon else 0) +
            (if (albercaChecked) precioAlberca else 0)

    // Obtener el calendario actual
    val calendar = Calendar.getInstance()

    // Mostrar DatePicker para seleccionar la fecha
    val datePickerDialog = DatePickerDialog(
        navController.context,
        { _, year, month, dayOfMonth -> fecha = "$dayOfMonth/${month + 1}/$year" },
        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Mostrar TimePicker para seleccionar la hora de inicio
    val startTimePickerDialog = TimePickerDialog(
        navController.context,
        { _, hourOfDay, minute -> horaInicio = String.format("%02d:%02d", hourOfDay, minute) },
        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
    )

    // Mostrar TimePicker para seleccionar la hora de cierre
    val endTimePickerDialog = TimePickerDialog(
        navController.context,
        { _, hourOfDay, minute -> horaCierre = String.format("%02d:%02d", hourOfDay, minute) },
        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
    )

    // Estado para controlar la visibilidad del diálogo
    var showDialog by remember { mutableStateOf(false) }

    // Función para agregar el evento al calendario
    fun addEventToCalendar(context: android.content.Context) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, "Reserva de Espacio")
            putExtra(CalendarContract.Events.DESCRIPTION, "Visitantes: $visitantes")

            // Parsear fecha y hora
            val parts = fecha.split("/")
            val dateCalendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, parts[2].toInt())
                set(Calendar.MONTH, parts[1].toInt() - 1)
                set(Calendar.DAY_OF_MONTH, parts[0].toInt())
            }
            val startHourParts = horaInicio.split(":")
            val endHourParts = horaCierre.split(":")

            val startCalendar = dateCalendar.clone() as Calendar
            startCalendar.set(Calendar.HOUR_OF_DAY, startHourParts[0].toInt())
            startCalendar.set(Calendar.MINUTE, startHourParts[1].toInt())

            val endCalendar = dateCalendar.clone() as Calendar
            endCalendar.set(Calendar.HOUR_OF_DAY, endHourParts[0].toInt())
            endCalendar.set(Calendar.MINUTE, endHourParts[1].toInt())

            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startCalendar.timeInMillis)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endCalendar.timeInMillis)
        }

        context.startActivity(intent)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header personalizable
        CustomHeader(title = "Reservaciones")

        // Contenido desplazable con LazyColumn
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            item {
                // Datos generales
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(text = "Datos generales", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Text(text = "Folio: 494993", fontSize = 16.sp, modifier = Modifier.fillMaxWidth().padding(start = 16.dp))

                // Espacio entre datos generales y espacios a reservar
                Spacer(modifier = Modifier.height(5.dp))

                // Campo para seleccionar fecha
                OutlinedTextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("Fecha") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Icon(Icons.Default.CalendarToday, contentDescription = "Seleccionar fecha")
                        }
                    }
                )

                // Campo para seleccionar hora de inicio
                OutlinedTextField(
                    value = horaInicio,
                    onValueChange = { horaInicio = it },
                    label = { Text("Hora de inicio") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { startTimePickerDialog.show() }) {
                            Icon(Icons.Default.AccessTime, contentDescription = "Seleccionar hora de inicio")
                        }
                    }
                )

                // Campo para seleccionar hora de cierre
                OutlinedTextField(
                    value = horaCierre,
                    onValueChange = { horaCierre = it },
                    label = { Text("Hora de cierre") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { endTimePickerDialog.show() }) {
                            Icon(Icons.Default.AccessTime, contentDescription = "Seleccionar hora de cierre")
                        }
                    }
                )

                // Espacio entre datos generales y espacios a reservar
                Spacer(modifier = Modifier.height(16.dp))

                // Espacios a reservar
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(text = "Espacio a reservar", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                // Checkboxes para los espacios
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = bañosChecked, onCheckedChange = { bañosChecked = it })
                    Text(text = "Baños (\$$precioBaños)")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = palapaChecked, onCheckedChange = { palapaChecked = it })
                    Text(text = "Palapa (\$$precioPalapa)")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = salonChecked, onCheckedChange = { salonChecked = it })
                    Text(text = "Salón (\$$precioSalon)")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = albercaChecked, onCheckedChange = { albercaChecked = it })
                    Text(text = "Alberca (\$$precioAlberca)")
                }

                // Espacio entre espacios a reservar y cantidad de visitantes
                Spacer(modifier = Modifier.height(16.dp))

                // Cantidad de visitantes
                OutlinedTextField(
                    value = visitantes,
                    onValueChange = { visitantes = it },
                    label = { Text("Visitantes") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )



                // Botón para agregar evento al calendario
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC4D9D2), // Color de fondo del botón
                        contentColor = Color.Black // Color del texto
                    )
                ) {
                    Text("Agregar evento a calendario")
                }

                // Confirmar si agregar al calendario
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Agregar evento al calendario") },
                        text = { Text("¿Desea agregar este evento a su calendario?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    addEventToCalendar(navController.context)
                                    showDialog = false
                                },
                                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFC4D9D2), // Color de fondo del botón
                                    contentColor = Color.Black // Color del texto
                                )
                            ) {
                                Text("Sí")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { showDialog = false },
                                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFC4D9D2), // Color de fondo del botón
                                    contentColor = Color.Black // Color del texto
                                )
                            ) {
                                Text("No")
                            }
                        }
                    )
                }

                // Espacio entre el botón y el total a pagar
                Spacer(modifier = Modifier.height(16.dp))

                // Total a pagar
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(text = "Método de pago", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    PaymentMethodButton(
                        iconRes = R.drawable.paypal,
                        text = "Pago en línea",
                        onClick = { navController.navigate("paypal/${total}") }
                    )

                    PaymentMethodButton(
                        iconRes = R.drawable.bank,
                        text = "Referencia bancaria",
                        onClick = { navController.navigate("banco/${total}") }
                    )
                }
            }
        }

        // Barra de navegación inferior
        BottomNavigationBar(navController = navController)
    }
}
@Composable
fun PaymentMethodButton(iconRes: Int, text: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
            .background(color = Color(0xFFC4DAD2)) // verde_claro
            .padding(16.dp) // Add padding inside the button
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier.size(48.dp) // Adjust the icon size here
        )
        Text(text = text, fontSize = 14.sp)
    }
}