package com.ita.condominio.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ita.condominio.biometrics.account.User
import com.ita.condominio.biometrics.account.UserRepository

class AccountDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)
    private val _user = MutableLiveData<User?>() // Make it nullable
    val user: LiveData<User?> get() = _user // Also expose as nullable LiveData

    init {
        // Replace with the ID of the logged-in user
        val idUsuarioLogueado = 1
        userRepository.getUserById(idUsuarioLogueado).observeForever { user ->
            _user.value = user // This now works because _user is nullable
        }
    }
}
