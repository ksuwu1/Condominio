package com.ita.condominio.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.CustomHeader2
import com.ita.condominio.database.DatabaseHelper
import com.ita.condominio.Network.Reservation
import java.util.*
// Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

// Para el objeto ReservationResponse y la clase Reservation
import com.ita.condominio.Network.ReservationResponse

// Para mostrar el Dialog (si ya lo usas)
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text

// Importaciones de clases generales de Compose
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import com.ita.condominio.Network.RetrofitInstance

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

    // Calcular el total de los servicios seleccionados
    val total = (if (bañosChecked) 100 else 0) +
            (if (palapaChecked) 500 else 0) +
            (if (salonChecked) 1500 else 0) +
            (if (albercaChecked) 1000 else 0)

    val calendar = Calendar.getInstance()

    // DatePicker y TimePickers
    val datePickerDialog = DatePickerDialog(
        navController.context,
        { _, year, month, dayOfMonth -> fecha = "$dayOfMonth/${month + 1}/$year" },
        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )
    val startTimePickerDialog = TimePickerDialog(
        navController.context,
        { _, hourOfDay, minute -> horaInicio = String.format("%02d:%02d", hourOfDay, minute) },
        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
    )
    val endTimePickerDialog = TimePickerDialog(
        navController.context,
        { _, hourOfDay, minute -> horaCierre = String.format("%02d:%02d", hourOfDay, minute) },
        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
    )

    // Estado para mostrar el diálogo de confirmación
    var showDialog by remember { mutableStateOf(false) }

    // Obtener el contexto para el DatabaseHelper
    val context = navController.context
    val dbHelper = DatabaseHelper(context)

    // Método para agregar la reservación a la base de datos
    fun insertReservacion() {
        // Crear la lista de servicios seleccionados
        val servicios = mutableListOf<String>()
        if (bañosChecked) servicios.add("Baños")
        if (palapaChecked) servicios.add("Palapa")
        if (salonChecked) servicios.add("Salón")
        if (albercaChecked) servicios.add("Alberca")

        // Crear el objeto de la reservación
        val reservation = Reservation(
            horainicio = horaInicio,
            horacierre = horaCierre,
            cantVisit = visitantes.toInt(),
            servicios = servicios,
            fecha = fecha,
            idUsuario = 1 // ID del usuario, necesitarás obtenerlo desde tu sesión o base de datos
        )

        // Usar Retrofit para hacer la solicitud al servidor
        val apiService = RetrofitInstance.api
        apiService.insertReservation(reservation).enqueue(object : Callback<ReservationResponse> {
            override fun onResponse(call: Call<ReservationResponse>, response: Response<ReservationResponse>) {
                if (response.isSuccessful) {
                    // La reservación fue insertada exitosamente en el servidor
                    // Opcional: Mostrar un mensaje de éxito
                    showDialog = true // Mostrar el diálogo de confirmación
                } else {
                    // Error en la respuesta del servidor
                    // Opcional: Mostrar mensaje de error
                }
            }

            override fun onFailure(call: Call<ReservationResponse>, t: Throwable) {
                // Error al realizar la solicitud
                // Opcional: Mostrar mensaje de error
            }
        })
    }

    // Layout principal
    Column(modifier = Modifier.fillMaxSize()) {
        CustomHeader2(navController = navController, title = "Reservaciones")

        LazyColumn(modifier = Modifier.weight(1f).padding(16.dp)) {
            item {
                // Campo para la fecha
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

                // Campo para la hora de inicio
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

                // Campo para la hora de cierre
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

                // Checkboxes para los servicios
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = bañosChecked, onCheckedChange = { bañosChecked = it })
                    Text(text = "Baños (100)")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = palapaChecked, onCheckedChange = { palapaChecked = it })
                    Text(text = "Palapa (500)")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = salonChecked, onCheckedChange = { salonChecked = it })
                    Text(text = "Salón (1500)")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = albercaChecked, onCheckedChange = { albercaChecked = it })
                    Text(text = "Alberca (1000)")
                }

                // Campo para la cantidad de visitantes
                OutlinedTextField(
                    value = visitantes,
                    onValueChange = { visitantes = it },
                    label = { Text("Visitantes") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                // Botón para confirmar y agregar la reservación
                Button(
                    onClick = {
                        insertReservacion()  // Llamar al método que inserta la reservación en la base de datos
                        showDialog = true  // Mostrar diálogo de confirmación
                    },
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4D9D2))
                ) {
                    Text("Confirmar Reservación")
                }

                // Confirmar si agregar la reservación al calendario
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Confirmar Reservación") },
                        text = { Text("¿Deseas agregar esta reservación al calendario?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    // Lógica para agregar al calendario (si deseas integrarlo)
                                    showDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4D9D2))
                            ) {
                                Text("Sí")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { showDialog = false },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4D9D2))
                            ) {
                                Text("No")
                            }
                        }
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