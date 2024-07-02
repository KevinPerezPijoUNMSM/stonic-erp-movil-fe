package com.stonic.stonic_erp_movil_fe.model

data class Usuario (
    val idusuario: Int,
    val nombres: String,
    val apellidos: String,
    val clave: String,
    val dni: String,
    val telefono: String,
    val idestado: Int,
    val correo: String,
    val nickname: String
)