package com.ita.condominio.biometrics.account

import android.content.Context
import androidx.lifecycle.LiveData

class UserRepository(private val context: Context) {
    private val userDao: UserDao = AppDatabase.getDatabase(context).userDao()

    fun getUserById(idUsuario: Int): LiveData<User> {
        return userDao.getUserById(idUsuario)
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }
}
