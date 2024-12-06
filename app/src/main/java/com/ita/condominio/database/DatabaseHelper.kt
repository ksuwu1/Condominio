package com.ita.condominio.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        // Crear las tablas
        db.execSQL(
            """
            CREATE TABLE Usuario (
                id_usuario INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                apellido_pat TEXT NOT NULL,
                apellido_mat TEXT NOT NULL,
                num_casa INTEGER NOT NULL,
                correo TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                tel_casa INTEGER NOT NULL,
                cel INTEGER NOT NULL
            );
            """
        )

        db.execSQL(
            """
            CREATE TABLE Servicio (
                id_servicio INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_servicio TEXT NOT NULL,
                precio REAL NOT NULL
            );
            """
        )

        db.execSQL(
            """
            CREATE TABLE Reservacion (
                id_reservacion INTEGER PRIMARY KEY AUTOINCREMENT,
                horainicio TEXT NOT NULL,
                horacierre TEXT NOT NULL,
                cant_visit INTEGER NOT NULL,
                id_servicio INTEGER NOT NULL,
                fecha TEXT NOT NULL,
                id_usuario INTEGER NOT NULL,
                FOREIGN KEY(id_servicio) REFERENCES Servicio(id_servicio),
                FOREIGN KEY(id_usuario) REFERENCES Usuario(id_usuario)
            );
            """
        )

        db.execSQL(
            """
            CREATE TABLE Visitantes (
                id_visitante INTEGER PRIMARY KEY AUTOINCREMENT,
                num_visit INTEGER NOT NULL,
                fecha_visit TEXT NOT NULL,
                id_usuario INTEGER NOT NULL,
                FOREIGN KEY(id_usuario) REFERENCES Usuario(id_usuario)
            );
            """
        )

        // Insertar datos iniciales
        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Eliminar tablas si se actualiza la base de datos
        db.execSQL("DROP TABLE IF EXISTS Visitantes")
        db.execSQL("DROP TABLE IF EXISTS Reservacion")
        db.execSQL("DROP TABLE IF EXISTS Servicio")
        db.execSQL("DROP TABLE IF EXISTS Usuario")
        onCreate(db)
    }

    // Método para insertar datos iniciales
    private fun insertInitialData(db: SQLiteDatabase) {
        // Usuario: Laia
        insertUserIfNotExists(db, ContentValues().apply {
            put("nombre", "Laia")
            put("apellido_pat", "Nieves")
            put("apellido_mat", "Luna")
            put("num_casa", 1)
            put("correo", "lainilu@gmail.com")
            put("password", "12345")
            put("tel_casa", 4491572244)
            put("cel", 4491572244)
        }, "lainilu@gmail.com")

        // Usuario: Fernando
        insertUserIfNotExists(db, ContentValues().apply {
            put("nombre", "Fernando")
            put("apellido_pat", "Rojas")
            put("apellido_mat", "Ruiz")
            put("num_casa", 2)
            put("correo", "ferouz@gmail.com")
            put("password", "olah12")
            put("tel_casa", 4491572275)
            put("cel", 4491548215)
        }, "ferouz@gmail.com")

        // Usuario: Perla
        insertUserIfNotExists(db, ContentValues().apply {
            put("nombre", "Perla")
            put("apellido_pat", "Sanchez")
            put("apellido_mat", "Lopez")
            put("num_casa", 2)
            put("correo", "perlosa@hotmail.com")
            put("password", "perla123")
            put("tel_casa", 4491572244)
            put("cel", 4491572244)
        }, "perlosa@hotmail.com")
    }

    private fun insertUserIfNotExists(db: SQLiteDatabase, user: ContentValues, email: String) {
        val cursor = db.query(
            "Usuario",
            arrayOf("id_usuario"),
            "correo = ?",
            arrayOf(email),
            null, null, null
        )

        if (cursor.count == 0) {
            val result = db.insert("Usuario", null, user)
            if (result == -1L) {
                // Log de error si no se pudo insertar
                android.util.Log.e("DatabaseHelper", "Error al insertar usuario con correo: $email")
            } else {
                // Log de éxito
                android.util.Log.i("DatabaseHelper", "Usuario insertado con correo: $email")
            }
        } else {
            // Log de usuario existente
            android.util.Log.w("DatabaseHelper", "Usuario ya existe con correo: $email")
        }
        cursor.close()
    }

    companion object {
        private const val DATABASE_NAME = "reservaServicios.db"
        private const val DATABASE_VERSION = 3
    }
}
