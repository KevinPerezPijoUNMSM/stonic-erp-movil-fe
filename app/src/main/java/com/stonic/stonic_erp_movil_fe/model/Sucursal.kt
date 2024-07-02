package com.stonic.stonic_erp_movil_fe.model

data class Sucursal (
    val idsucursal: Int,
    val numero: Int,
    val nombre: String,
    val telefono: String,
    val idnegocio: Int,
    val flagescentral: Boolean,
    val localizacionx: Float,
    val localizaciony: Float
)