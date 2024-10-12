package com.ita.condominio.screens

import com.ita.condominio.R
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
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

import java.util.*

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        // Datos generales
        Text(text = "Datos generales", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = "Folio: 494993", fontSize = 16.sp)

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
        Text(text = "Espacio a reservar", fontSize = 20.sp, fontWeight = FontWeight.Bold)

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

        // Total a pagar
        Text(text = "Total a pagar: \$$total", fontSize = 16.sp, fontWeight = FontWeight.Bold)

        // Espacio entre total a pagar y método de pago
        Spacer(modifier = Modifier.height(16.dp))

        // Método de pago
        Text(text = "Método de pago", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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


@Composable
fun PaymentMethodButton(iconRes: Int, text: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick).padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier.size(48.dp) // Ajusta el tamaño del icono aquí
        )
        Text(text = text, fontSize = 14.sp)
    }
}
