package com.ita.condominio.Network

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ita.condominio.database.DatabaseManager
import kotlinx.coroutines.launch

class AccountDetailsViewModel(application: Application) : AndroidViewModel(application) {

    // Se obtiene el contexto de la aplicación correctamente
    private val databaseManager = DatabaseManager(application)

    private val _usuario = MutableLiveData<UserResponse?>()
    val usuario: LiveData<UserResponse?> get() = _usuario

    init {
        obtenerUsuario() // Se obtiene automáticamente cuando se crea el ViewModel
    }

    fun obtenerUsuario() {
        Log.e("AccountDetailsViewModel", "ENTRA AL VIEWMODEL")
        viewModelScope.launch {
            try {
                val usuarioObtenido = databaseManager.obtenerUsuario()
                _usuario.value = usuarioObtenido
            } catch (e: Exception) {
                Log.e("AccountDetailsViewModel", "Error al obtener usuario: ${e.message}")
            }
        }
    }
}
