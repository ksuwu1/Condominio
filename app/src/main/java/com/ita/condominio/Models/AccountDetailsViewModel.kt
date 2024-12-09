package com.ita.condominio.Models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ita.condominio.Network.ApiService
import com.ita.condominio.Network.RetrofitInstance
import com.ita.condominio.Network.UserRequest
import com.ita.condominio.database.DatabaseManager
import kotlinx.coroutines.launch
import retrofit2.Response

class AccountDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val databaseManager = DatabaseManager(application)

    private val _userDetails = MutableLiveData<List<ModelUser>?>()
    val userDetails: LiveData<List<ModelUser>?> get() = _userDetails

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchUserDetails(correo: String) {
        viewModelScope.launch {
            _error.value = null
            try {
                // Llamada al API con el correo como parámetro de consulta
                val response = RetrofitInstance.api.getUserByEmail(correo)
                if (response.isSuccessful) {
                    response.body()?.let {
                        // Mapea el `UserResponse` a `ModelUser` si la respuesta es exitosa
                        val user = ModelUser(
                            id_usuario = it.id_usuario,
                            nombre = it.nombre,
                            apellido_pat = it.apellido_pat,
                            apellido_mat = it.apellido_mat,
                            num_casa = it.num_casa,
                            correo = it.correo,
                            password = it.password,
                            tel_casa = it.tel_casa,
                            cel = it.cel
                        )
                        _userDetails.value = listOf(user)  // Pasamos una lista con un solo usuario
                        insertarUsuarioEnBD(listOf(user))
                    } ?: run {
                        _error.value = "No se encontraron datos."
                    }
                } else {
                    _error.value = "Error al obtener los datos: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
                e.printStackTrace()
            }
        }
    }


    private fun insertarUsuarioEnBD(users: List<ModelUser>) {
        viewModelScope.launch {
            try {
                for (user in users) {
                    val userRequest = UserRequest(
                        id_usuario = user.id_usuario,
                        nombre = user.nombre,
                        apellido_pat = user.apellido_pat,
                        apellido_mat = user.apellido_mat,
                        num_casa = user.num_casa,
                        correo = user.correo,
                        password = user.password,
                        tel_casa = user.tel_casa,
                        cel = user.cel
                    )
                    // Pasar los valores individuales en lugar de userRequest
                    databaseManager.insertarUsuario(
                        userRequest.id_usuario,
                        userRequest.nombre,
                        userRequest.apellido_pat,
                        userRequest.apellido_mat,
                        userRequest.num_casa,
                        userRequest.correo,
                        userRequest.password,
                        userRequest.tel_casa,
                        userRequest.cel
                    )
                }
            } catch (e: Exception) {
                Log.e("AccountDetailsVM", "Error al insertar usuario: ${e.message}")
            }
        }
    }
}
