package com.ita.condominio.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ita.condominio.database.DatabaseHelper

data class User(
    val nombre: String,
    val apellidoPat: String,
    val apellidoMat: String,
    val numCasa: Int,
    val correo: String,
    val telCasa: Long,
    val cel: Long
)

class DBHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS usuarios (" +
                    "nombre TEXT, " +
                    "apellido_pat TEXT, " +
                    "apellido_mat TEXT, " +
                    "num_casa INTEGER, " +
                    "correo TEXT PRIMARY KEY, " +
                    "tel_casa INTEGER, " +
                    "cel INTEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS usuarios")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "condominio.db"
        private const val DATABASE_VERSION = 1
    }
}

class UserRepository(context: Context) {
    private val dbHelper = DBHelper(context)

    fun getUserDetails(email: String): User? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            "usuarios", // Nombre de la tabla
            arrayOf("nombre", "apellido_pat", "apellido_mat", "num_casa", "correo", "tel_casa", "cel"),
            "correo = ?", // Filtro de selección
            arrayOf(email),
            null, null, null
        )

        return if (cursor.moveToFirst()) {
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val apellidoPat = cursor.getString(cursor.getColumnIndexOrThrow("apellido_pat"))
            val apellidoMat = cursor.getString(cursor.getColumnIndexOrThrow("apellido_mat"))
            val numCasa = cursor.getInt(cursor.getColumnIndexOrThrow("num_casa"))
            val correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"))
            val telCasa = cursor.getLong(cursor.getColumnIndexOrThrow("tel_casa"))
            val cel = cursor.getLong(cursor.getColumnIndexOrThrow("cel"))

            User(nombre, apellidoPat, apellidoMat, numCasa, correo, telCasa, cel)
        } else {
            null
        }.also {
            cursor.close()
            db.close()
        }
        fun getUserDetails(email: String): User? {
            val db: SQLiteDatabase = dbHelper.readableDatabase
            return db.query(
                "usuarios",
                arrayOf("nombre", "apellido_pat", "apellido_mat", "num_casa", "correo", "tel_casa", "cel"),
                "correo = ?",
                arrayOf(email),
                null, null, null
            ).use { cursor ->
                if (cursor.moveToFirst()) {
                    val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                    val apellidoPat = cursor.getString(cursor.getColumnIndexOrThrow("apellido_pat"))
                    val apellidoMat = cursor.getString(cursor.getColumnIndexOrThrow("apellido_mat"))
                    val numCasa = cursor.getInt(cursor.getColumnIndexOrThrow("num_casa"))
                    val correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"))
                    val telCasa = cursor.getLong(cursor.getColumnIndexOrThrow("tel_casa"))
                    val cel = cursor.getLong(cursor.getColumnIndexOrThrow("cel"))

                    User(nombre, apellidoPat, apellidoMat, numCasa, correo, telCasa, cel)
                } else {
                    null
                }
            }.also {
                db.close()
            }
        }

    }
}
