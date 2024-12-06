package com.ita.condominio.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class DatabaseManager(context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    // Abre la base de datos
    fun openDatabase() {
        database = dbHelper.writableDatabase
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

    // Verifica si el correo y contraseña son válidos
    fun isValidUser(email: String, password: String): Boolean {
        openDatabase()
        val cursor = database.rawQuery(
            "SELECT * FROM Usuario WHERE correo = ? AND password = ?",
            arrayOf(email, password)
        )
        val isValid = cursor.count > 0
        cursor.close()
        closeDatabase()
        return isValid
    }
}