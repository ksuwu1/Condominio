package com.ita.condominio.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ita.condominio.Models.ModelMorosos
import com.ita.condominio.Network.RetrofitInstance
import com.ita.condominio.database.DatabaseManager
import kotlinx.coroutines.launch

class MorosoViewModel(application: Application) : AndroidViewModel(application) {

    private val databaseManager = DatabaseManager(application) // ✅ Instancia de la BD

    private val _morosos = MutableLiveData<List<ModelMorosos>>()
    val morosos: LiveData<List<ModelMorosos>> get() = _morosos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchMorosos() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = RetrofitInstance.api.getMorosos()
                if (response.isSuccessful) {
                    val morosos = response.body()?.map {
                        ModelMorosos(
                            id_moroso = it.id_moroso,
                            casa = it.casa,
                            descripcion = it.descripcion_fecha,
                            detalleDescripcion = it.detalle,
                            cantidad = it.cantidad
                        )
                    } ?: emptyList()

                    // Actualizar la lista de morosos observada en la UI
                    _morosos.value = morosos

                    // Insertar morosos en la base de datos
                    insertarMorososEnBD(morosos)
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

    private fun insertarMorososEnBD(morosos: List<ModelMorosos>) {
        viewModelScope.launch {
            try {
                databaseManager.insertarMorosos(morosos) // ✅ Ya no necesitas "context = getApplication()"
            } catch (e: Exception) {
                Log.e("ViewModel", "Error al insertar morosos: ${e.message}")
            }
        }
    }
}
