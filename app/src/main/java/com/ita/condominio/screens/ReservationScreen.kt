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
import com.ita.condominio.CustomHeader2
import com.ita.condominio.Network.Reservacion
import com.ita.condominio.Network.ReservacionRespuesta
import com.ita.condominio.Network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Response
import org.chromium.base.Callback
import retrofit2.Call

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

    // Función para obtener los servicios seleccionados
    fun getServiciosSeleccionados(): String {
        val serviciosSeleccionados = mutableListOf<String>()

        if (bañosChecked) serviciosSeleccionados.add("Baños")
        if (palapaChecked) serviciosSeleccionados.add("Palapa")
        if (salonChecked) serviciosSeleccionados.add("Salón")
        if (albercaChecked) serviciosSeleccionados.add("Alberca")

        return serviciosSeleccionados.joinToString(", ")
    }

    // Función para realizar la reservación
    suspend fun insertarReservaciones() {
        // Crear la reservación con los datos actuales
        val reservacion = Reservacion(
            id_reservacion = 0, // El id puede ser autogenerado por la base de datos
            id_usuario = 1, // Aquí debes obtener el ID del usuario que está haciendo la reserva
            hora_inicio = horaInicio,
            hora_cierre = horaCierre,
            cant_visit = visitantes.toInt(),
            servicios = getServiciosSeleccionados(),
            fecha = fecha
        )

        try {
            // Llamar a la API usando Retrofit
            val respuesta = RetrofitInstance.api.insertarReservaciones(reservacion)

            // Verifica si la respuesta es exitosa
            if (respuesta.success) {
                // Si la respuesta es exitosa, mostrar un mensaje
                showDialog = true
            } else {
                // Si no es exitosa, manejar el error
                showDialog = false
            }
        } catch (e: Exception) {
            // En caso de error en la conexión o cualquier otro tipo de fallo
            showDialog = false
        }
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header personalizable
        CustomHeader2(navController = navController, title = "Reservaciones")

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
                    Text(text = "Baños ($precioBaños)", fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = palapaChecked, onCheckedChange = { palapaChecked = it })
                    Text(text = "Palapa ($precioPalapa)", fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = salonChecked, onCheckedChange = { salonChecked = it })
                    Text(text = "Salón ($precioSalon)", fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = albercaChecked, onCheckedChange = { albercaChecked = it })
                    Text(text = "Alberca ($precioAlberca)", fontSize = 16.sp)
                }

                // Espacio entre los checkboxes y el total
                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar el total
                Text(text = "Total a pagar: $total", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            item {
                // Botón para realizar la reservación
                Button(
                    onClick = {
                        // Llamada dentro de una corrutina para ejecutar la función suspensiva
                        CoroutineScope(Dispatchers.Main).launch {
                            insertarReservaciones()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1C76FF))
                ) {
                    Text(text = "Realizar Reservación")
                }

            }
        }

        // Barra de navegación inferior
        BottomNavigationBar(navController = navController)
    }

    // Mostrar diálogo de confirmación o error
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Reservación exitosa") },
            text = { Text("Tu reservación se ha realizado correctamente.") },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Aceptar")
                }
            }
        )
    }
}

fun insertarReservaciones() {
    TODO("Not yet implemented")
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