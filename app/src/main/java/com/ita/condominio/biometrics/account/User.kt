package com.ita.condominio.biometrics.account

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuario")
data class User(
    @PrimaryKey(autoGenerate = true) val id_usuario: Int = 0,
    val nombre: String,
    val apellido_pat: String,
    val apellido_mat: String,
    val num_casa: Int,
    val correo: String,
    val password: String,
    val tel_casa: String,
    val cel: String
)

data class UserResponse(
    val id_usuario: String,
    val nombre: String,
    val apellido_pat: String,
    val apellido_mat: String,
    val tel_casa: String,
    val cel: String,
    val correo: String
)
data class UserDetails(
    var name: String,
    var lastNameP: String,
    var lastNameM: String,
    var phone: String,
    var mobile: String,
    var email: String
)


