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
    private val _usuario = MutableLiveData<UserResponse?>()
    val usuario: LiveData<UserResponse?> get() = _usuario

    val id_usuario = MutableLiveData<Int>()
    val nombre = MutableLiveData<String>()
    val apellidoPat = MutableLiveData<String>()
    val apellidoMat = MutableLiveData<String>()
    val casa = MutableLiveData<Int>()
    val password = MutableLiveData<String>()
    val telCasa = MutableLiveData<String>()
    val cel = MutableLiveData<String>()
    val correo = MutableLiveData<String>()

    init {
        obtenerUsuario()
    }

    // Obtener los datos del usuario
    fun obtenerUsuario() {
        viewModelScope.launch {
            val usuarioObtenido = DatabaseManager(getApplication()).obtenerUsuario()
            _usuario.value = usuarioObtenido
            id_usuario.value = usuarioObtenido?.id_usuario
            nombre.value = usuarioObtenido?.nombre
            apellidoPat.value = usuarioObtenido?.apellido_pat
            apellidoMat.value = usuarioObtenido?.apellido_mat
            casa.value = usuarioObtenido?.num_casa
            password.value = usuarioObtenido?.password
            telCasa.value = usuarioObtenido?.tel_casa
            cel.value = usuarioObtenido?.cel
            correo.value = usuarioObtenido?.correo
        }
    }

    // Función para actualizar los datos del usuario en la base de datos
    fun guardarCambios(
        nombre: String,
        apellidoPat: String,
        apellidoMat: String,
        telCasa: String,
        celular: String,
        correo: String
    ) {
        viewModelScope.launch {
            try {
                val usuarioActualizado = UserResponse(
                    id_usuario.value ?: 0, // Mantiene el ID existente
                    nombre,
                    apellidoPat,
                    apellidoMat,
                    casa.value ?: 0, // Mantiene el número de casa
                    correo,
                    password.value ?: "", // Mantiene la contraseña actual
                    telCasa,
                    celular
                )
                val result = DatabaseManager(getApplication()).actualizarUsuario(usuarioActualizado)

                if (result) {
                    obtenerUsuario() // Vuelve a obtener los datos actualizados
                    Log.e("AccountDetailsViewModel", "Datos actualizados correctamente")
                } else {
                    Log.e("AccountDetailsViewModel", "Error al actualizar los datos.")
                }
            } catch (e: Exception) {
                Log.e("AccountDetailsViewModel", "Error al guardar cambios: ${e.message}")
            }
        }
    }


}
