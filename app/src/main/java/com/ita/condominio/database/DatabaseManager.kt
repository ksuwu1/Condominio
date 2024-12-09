package com.ita.condominio.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.ita.condominio.Network.LoginRequest
import com.ita.condominio.Network.RetrofitInstance
import com.ita.condominio.Models.ModelMorosos
import com.ita.condominio.Network.Expense
import com.ita.condominio.Network.MaintenanceIncome
import com.ita.condominio.Network.ModelAvisos
import com.ita.condominio.Network.Moroso
import com.ita.condominio.Network.ReservationIncome
import com.ita.condominio.Network.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.ita.condominio.Network.Reservacion

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
                    insertarUsuario(userResponse.id_usuario, userResponse.nombre, userResponse.apellido_pat, userResponse.apellido_mat, userResponse.num_casa, userResponse.correo, userResponse.password, userResponse.tel_casa, userResponse.cel)
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
        id_usuario: Int,
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
            INSERT INTO Usuario (id_usuario, nombre, apellido_pat, apellido_mat, num_casa, correo, password, tel_casa, cel)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """
            // Ejecutar la inserción con valores
            database.execSQL(insertQuery, arrayOf(id_usuario, nombre, apellidoPat, apellidoMat, numCasa, correo, password, telCasa, cel))
            Log.e("DatabaseManager", "Usuario insertado con éxito")
        } catch (e: Exception) {
            Log.e("DatabaseManager", "Error al insertar usuario: ${e.message}")
        } finally {
            closeDatabase() // Método que cierra la base de datos
        }
    }

    fun insertarMorosos(morosos: List<ModelMorosos>) {
        openDatabase() // Abre la base de datos solo una vez
        try {
            val insertQuery = """
            INSERT OR IGNORE INTO Morosos (id_moroso, casa, descripcion, detalle, cantidad)
            VALUES (?, ?, ?, ?, ?)
        """

            database.beginTransaction() // Inicia la transacción
            morosos.forEach { moroso ->
                database.execSQL(
                    insertQuery,
                    arrayOf(moroso.id_moroso, moroso.casa, moroso.descripcion, moroso.detalleDescripcion, moroso.cantidad)
                )
            }
            database.setTransactionSuccessful() // Marca la transacción como exitosa
            Log.e("DatabaseManager", "Morosos insertados con éxito")
        } catch (e: Exception) {
            Log.e("DatabaseManager", "Error al insertar morosos: ${e.message}")
        } finally {
            database.endTransaction() // Finaliza la transacción
            closeDatabase() // Cierra la base de datos solo una vez
        }
    }

    fun insertarAvisos(a: List<ModelAvisos>) {
        openDatabase() // Abre la base de datos solo una vez
        try {
            val insertQuery = """
            INSERT OR IGNORE INTO Aviso (id_aviso, tipo_aviso, titulo, fecha, descripcion)
            VALUES (?, ?, ?, ?, ?)
        """

            database.beginTransaction() // Inicia la transacción
            a.forEach { avisos -> // Itera sobre los avisos
                database.execSQL(
                    insertQuery,
                    arrayOf(avisos.id_aviso, avisos.tipo_aviso, avisos.titulo, avisos.fecha, avisos.descripcion)
                )
            }
            database.setTransactionSuccessful() // Marca la transacción como exitosa
            Log.e("DatabaseManager", "Avisos insertados con éxito")
        } catch (e: Exception) {
            Log.e("DatabaseManager", "Error al insertar avisos: ${e.message}")
        } finally {
            database.endTransaction() // Finaliza la transacción
            closeDatabase() // Cierra la base de datos solo una vez
        }
    }


    fun insertarMantenimientoIngresos(ingresos: List<MaintenanceIncome>) {
        openDatabase()
        try {
            val insertQuery = """
            INSERT OR IGNORE INTO Mantenimiento (M_folio, casa, nombre, mes, cantidad, transferencia)
            VALUES (?, ?, ?, ?, ?, ?)
        """
            database.beginTransaction()
            ingresos.forEach { ingreso ->
                database.execSQL(
                    insertQuery,
                    arrayOf(ingreso.M_folio, ingreso.casa, ingreso.nombre, ingreso.mes, ingreso.cantidad, if (ingreso.transferencia) 1 else 0)
                )
            }
            database.setTransactionSuccessful()
            Log.e("DatabaseManager", "Ingresos de mantenimiento insertados con éxito")
        } catch (e: Exception) {
            Log.e("DatabaseManager", "Error al insertar ingresos de mantenimiento: ${e.message}")
        } finally {
            database.endTransaction()
            closeDatabase()
        }
    }

    fun insertarReservaIngresos(ingresos: List<ReservationIncome>) {
        openDatabase()
        try {
            val insertQuery = """
            INSERT OR IGNORE INTO Ingre_Reserva (R_folio, casa, descripcion, fecha, cantidad, transferencia)
            VALUES (?, ?, ?, ?, ?, ?)
        """
            database.beginTransaction()
            ingresos.forEach { ingreso ->
                database.execSQL(
                    insertQuery,
                    arrayOf(ingreso.R_folio, ingreso.casa, ingreso.descripcion, ingreso.fecha, ingreso.cantidad, if (ingreso.transferencia) 1 else 0)
                )
            }
            database.setTransactionSuccessful()
            Log.e("DatabaseManager", "Ingresos de reserva insertados con éxito")
        } catch (e: Exception) {
            Log.e("DatabaseManager", "Error al insertar ingresos de reserva: ${e.message}")
        } finally {
            database.endTransaction()
            closeDatabase()
        }
    }

    fun insertarEgresos(egresos: List<Expense>) {
        openDatabase()
        try {
            val insertQuery = """
            INSERT OR IGNORE INTO Egreso (E_folio, descripcion, fecha, cantidad)
            VALUES (?, ?, ?, ?)
        """
            database.beginTransaction()
            egresos.forEach { egreso ->
                database.execSQL(
                    insertQuery,
                    arrayOf(egreso.E_folio, egreso.descripcion, egreso.fecha, egreso.cantidad)
                )
            }
            database.setTransactionSuccessful()
            Log.e("DatabaseManager", "Egresos insertados con éxito")
        } catch (e: Exception) {
            Log.e("DatabaseManager", "Error al insertar egresos: ${e.message}")
        } finally {
            database.endTransaction()
            closeDatabase()
        }
    }

    suspend fun obtenerUsuario(): UserResponse? {
        Log.e("DatabaseManager", "ENTRA A LA CONSULTA")
        return withContext(Dispatchers.IO) {
            var user: UserResponse? = null

            try {
                openDatabase() // Abre la base de datos

                val cursor = database.rawQuery("SELECT * FROM Usuario", null)

                if (cursor.moveToFirst()) {
                    val id_usuario = cursor.getInt(cursor.getColumnIndexOrThrow("id_usuario"))
                    val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                    val apellidoPat = cursor.getString(cursor.getColumnIndexOrThrow("apellido_pat"))
                    val apellidoMat = cursor.getString(cursor.getColumnIndexOrThrow("apellido_mat"))
                    val numCasa = cursor.getInt(cursor.getColumnIndexOrThrow("num_casa"))
                    val correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"))
                    val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
                    val telCasa = cursor.getString(cursor.getColumnIndexOrThrow("tel_casa"))
                    val cel = cursor.getString(cursor.getColumnIndexOrThrow("cel"))

                    user = UserResponse(id_usuario, nombre, apellidoPat, apellidoMat, numCasa, correo, password, telCasa, cel)
                }
                if (cursor.moveToFirst()) {
                    Log.e("DatabaseManager", "Usuario encontrado")
                } else {
                    Log.e("DatabaseManager", "No se encontró ningún usuario en la tabla Usuario")
                }

                cursor.close() // Cierra el cursor
            } catch (e: Exception) {
                Log.e("DatabaseManager", "Error al obtener usuario: ${e.message}")
            } finally {
                closeDatabase() // Cierra la base de datos
            }

            return@withContext user
        }
    }

    fun getColumnIndexSafe(cursor: Cursor, columnName: String): String? {
        val index = cursor.getColumnIndex(columnName)
        return if (index >= 0) cursor.getString(index) else null
    }

    fun actualizarUsuario(usuario: UserResponse): Boolean {
        Log.e("AccountDetailsViewModel", "ENTRO A ACTUALIZAR" + usuario.cel)

        val db = dbHelper.writableDatabase
        val celular = usuario.cel

        try {
            // Inicia la transacción
            db.beginTransaction()

            val updateQuery = """
            INSERT OR REPLACE INTO Usuario (id_usuario, nombre, apellido_pat, apellido_mat, num_casa, correo, password, tel_casa, cel)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """

            // Ejecuta el update o insert
            db.execSQL(
                updateQuery,
                arrayOf(usuario.id_usuario, usuario.nombre, usuario.apellido_pat, usuario.apellido_mat, usuario.num_casa, usuario.correo, usuario.password, usuario.tel_casa, usuario.cel)
            )

            // Marca la transacción como exitosa
            db.setTransactionSuccessful()

            val query = "SELECT * FROM Usuario WHERE id_usuario = ?"
            val cursor = db.rawQuery(query, arrayOf(usuario.id_usuario.toString()))

            if (cursor.moveToFirst()) {
                val cel = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                Log.e("DatabaseManager", "Celular actualizado: $cel")
            } else {
                Log.e("DatabaseManager", "No se encontró el usuario con id_usuario: ${usuario.id_usuario}")
            }

            return true
        } catch (e: Exception) {
            Log.e("DatabaseManager", "Error al actualizar o insertar usuario: ${e.message} ${celular}" )
            return false
        } finally {
            // Finaliza la transacción y cierra la base de datos
            db.endTransaction()
            closeDatabase()
        }
    }
    
    fun insertarReservaciones(reservaciones: List<Reservacion>) {
        openDatabase() // Abre la base de datos una vez
        try {
            val insertQuery = """
            INSERT OR IGNORE INTO Reservacion (id_reservacion, id_usuario, hora_inicio, hora_cierre, cant_visit, servicios, fecha)
            VALUES (?, ?, ?, ?, ?, ?,?)
        """

            database.beginTransaction() // Inicia la transacción para garantizar atomicidad
            reservaciones.forEach { reservacion ->
                database.execSQL(
                    insertQuery,
                    arrayOf(
                        reservacion.id_reservacion,
                        reservacion.id_usuario,
                        reservacion.hora_inicio,
                        reservacion.hora_cierre,
                        reservacion.cant_visit,
                        reservacion.servicios,
                        reservacion.fecha
                    )
                )
            }
            database.setTransactionSuccessful() // Marca la transacción como exitosa
            Log.e("DatabaseManager", "Reservaciones insertadas con éxito")
        } catch (e: Exception) {
            Log.e("DatabaseManager", "Error al insertar reservaciones: ${e.message}")
        } finally {
            database.endTransaction() // Finaliza la transacción
            closeDatabase() // Cierra la base de datos
        }
    }

}
