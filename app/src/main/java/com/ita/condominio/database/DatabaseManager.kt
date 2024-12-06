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

    // Verifica si hay usuarios en la tabla "Usuario"
    fun verificarUsuarios() {
        openDatabase()
        val cursor = database.rawQuery("SELECT * FROM Usuario", null)
        if (cursor.count > 0) {
            Log.d("DatabaseManager", "Usuarios encontrados: ${cursor.count}")
        } else {
            Log.d("DatabaseManager", "No se encontraron usuarios")
        }
        cursor.close()
        closeDatabase()
    }

    // Verifica si el correo y contraseña son válidos (sin composable)
    suspend fun isValidUser(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        try {
            val loginRequest = LoginRequest(email, password)
            // Aquí la llamada a Retrofit API se hace de manera síncrona o con una corutina desde el lugar adecuado
            val response = RetrofitInstance.api.loginUser(loginRequest)

            if (response.isSuccessful) {
                val userResponse = response.body()
                if (userResponse != null) {
                    // Si el usuario existe, inserta los datos en la base de datos local
                    insertUserIfNotExists(userResponse)
                    onResult(true, null)  // Usuario válido, no hay error
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

    private fun insertUserIfNotExists(user: UserResponse) {
        val dbManager = DatabaseManager(context)
        val db = dbManager.openDatabase()

        val values = ContentValues().apply {
            put("id_usuario", user.id_usuario)
            put("nombre", user.nombre)
            put("apellido_pat", user.apellido_pat)
            put("apellido_mat", user.apellido_mat)
            put("num_casa", user.num_casa)
            put("correo", user.correo)
            put("tel_casa", user.tel_casa)
            put("cel", user.cel)
        }

        val cursor = db.query(
            "Usuario",
            arrayOf("id_usuario"),
            "correo = ?",
            arrayOf(user.correo),
            null, null, null
        )

        if (cursor.count == 0) {
            val result = db.insert("Usuario", null, values)
            if (result == -1L) {
                Log.e("DatabaseHelper", "Error al insertar usuario")
            } else {
                Log.i("DatabaseHelper", "Usuario insertado con correo: ${user.correo}")
            }
        } else {
            Log.w("DatabaseHelper", "Usuario ya existe con correo: ${user.correo}")
        }

        cursor.close()
        dbManager.closeDatabase()
    }
}
