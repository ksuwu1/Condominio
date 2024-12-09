package com.ita.condominio.Network

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ita.condominio.database.DatabaseManager
import kotlinx.coroutines.launch

class AvisoViewModel(application: Application) : AndroidViewModel(application) {

    private val databaseManager = DatabaseManager(application) // Instancia de la BD

    private val _avisos = MutableLiveData<List<ModelAvisos>>()
    val avisos: LiveData<List<ModelAvisos>> get() = _avisos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchAvisos() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = RetrofitInstance.api.getAvisos() // Aquí usas tu API para obtener los avisos
                if (response.isSuccessful) {
                    val avisos = response.body()?.map {
                        ModelAvisos(
                            id_aviso = it.id_aviso,
                            tipo_aviso = it.tipo_aviso,
                            titulo = it.titulo,
                            fecha = it.fecha,
                            descripcion = it.descripcion
                        )
                    } ?: emptyList()

                    // Actualizar la lista de avisos observada en la UI
                    _avisos.value = avisos

                    // Insertar los avisos en la base de datos
                    insertarAvisosEnBD(avisos)
                } else {
                    _error.value = "Error al obtener los datos: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun insertarAvisosEnBD(avisos: List<ModelAvisos>) {
        viewModelScope.launch {
            try {
                databaseManager.insertarAvisos(avisos) // Insertar los avisos en la BD
            } catch (e: Exception) {
                Log.e("ViewModel", "Error al insertar avisos: ${e.message}")
            }
        }
    }
}
