package com.ita.condominio.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.ita.condominio.Network.LoginRequest
import com.ita.condominio.Network.RetrofitInstance
import com.ita.condominio.Network.UserResponse
import kotlinx.coroutines.launch
import android.content.ContentValues

class DatabaseManager(private val context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    // Abre la base de datos
    fun openDatabase(): SQLiteDatabase {
        database = dbHelper.writableDatabase
        return database
    }

    // Cierra la base de datos si está abierta
    fun closeDatabase() {
        if (::database.isInitialized && database.isOpen) {
            database.close()
        }
    }

    // Verifica si el correo y contraseña son válidos
    suspend fun isValidUser(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        try {
            val loginRequest = LoginRequest(email, password)

            val response = RetrofitInstance.api.loginUser(loginRequest)

            if (response.isSuccessful) {
                val userResponse = response.body()
                if (userResponse != null) {
                    openDatabase()
                    database.delete("Usuario", null, null)
                    closeDatabase()

                    // Si el usuario existe, inserta los datos en la base de datos local
                    insertarUsuario(userResponse.nombre, userResponse.apellido_pat, userResponse.apellido_mat, userResponse.num_casa, userResponse.correo, userResponse.password, userResponse.tel_casa, userResponse.cel)
                    onResult(true, null)
                } else {
                    onResult(false, "Usuario no encontrado")
                    
                }
            } else {
                onResult(false, "Error en la autenticación")
            }
        } catch (e: Exception) {
            onResult(false, "Error de red: ${e.message}")
        }
    }

    fun insertarUsuario(
        nombre: String,
        apellidoPat: String,
        apellidoMat: String,
        numCasa: Int,
        correo: String,
        password: String,
        telCasa: String,
        cel: String
    ) {
        openDatabase() // Método que abre la base de datos --
        try {
            // Sentencia SQL para insertar un usuario
            val insertQuery = """
            INSERT INTO Usuario (nombre, apellido_pat, apellido_mat, num_casa, correo, password, tel_casa, cel)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """
            // Ejecutar la inserción con valores
            database.execSQL(insertQuery, arrayOf(nombre, apellidoPat, apellidoMat, numCasa, correo, password, telCasa, cel))
            Log.e("DatabaseManager", "Usuario insertado con éxito")
        } catch (e: Exception) {
            Log.e("DatabaseManager", "Error al insertar usuario: ${e.message}")
        } finally {
            closeDatabase() // Método que cierra la base de datos
        }
    }



}
