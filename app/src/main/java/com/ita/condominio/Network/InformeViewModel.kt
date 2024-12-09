package com.ita.condominio.Network

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.ita.condominio.Models.*
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ita.condominio.database.DatabaseManager

class InformeViewModel (application: Application) : AndroidViewModel(application) {
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses

    private val _maintenanceIncomes = MutableStateFlow<List<MaintenanceIncome>>(emptyList())
    val maintenanceIncomes: StateFlow<List<MaintenanceIncome>> = _maintenanceIncomes

    private val _reservationIncomes = MutableStateFlow<List<ReservationIncome>>(emptyList())
    val reservationIncomes: StateFlow<List<ReservationIncome>> = _reservationIncomes

    fun fetchData() {
        viewModelScope.launch {
            try {
                _expenses.value = RetrofitInstance.api.getExpenses()
                val egresos = RetrofitInstance.api.getExpenses()
                _maintenanceIncomes.value = RetrofitInstance.api.getMaintenanceIncomes()
                val mantenimiento = RetrofitInstance.api.getMaintenanceIncomes()
                _reservationIncomes.value = RetrofitInstance.api.getReservationIncomes()
                val ingrereserva = RetrofitInstance.api.getReservationIncomes()

                insertarEgresosEnBD(egresos)
                insertarMantenimientoEnBD(mantenimiento)
                insertarIngresoReservaEnBD(ingrereserva)
            } catch (e: Exception) {
                // Manejo de errores (ej: mostrar mensaje al usuario)
            }

        }
    }

    private val databaseManager = DatabaseManager(application)

    private fun insertarEgresosEnBD(egresos: List<Expense>) {
        viewModelScope.launch {
            try {
                databaseManager.insertarEgresos(egresos) // ✅ Ya no necesitas "context = getApplication()"
            } catch (e: Exception) {
                Log.e("ViewModel", "Error al insertar egresos: ${e.message}")
            }
        }
    }

    private fun insertarMantenimientoEnBD(mantenimiento: List<MaintenanceIncome>) {
        viewModelScope.launch {
            try {
                databaseManager.insertarMantenimientoIngresos(mantenimiento)// ✅ Ya no necesitas "context = getApplication()"
            } catch (e: Exception) {
                Log.e("ViewModel", "Error al insertar mantenimiento: ${e.message}")
            }
        }
    }

    private fun insertarIngresoReservaEnBD(ingreReserva: List<ReservationIncome>) {
        viewModelScope.launch {
            try {
                databaseManager.insertarReservaIngresos(ingreReserva)// ✅ Ya no necesitas "context = getApplication()"
            } catch (e: Exception) {
                Log.e("ViewModel", "Error al insertar Ingreso de reservaciones: ${e.message}")
            }
        }
    }
}
