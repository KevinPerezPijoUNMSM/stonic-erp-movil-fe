package com.stonic.stonic_erp_movil_fe.model
import java.util.Date
data class TransaccionB2C (
    val idtransaccionb2c: Int,
    val idcomprador: Int,
    val idvendedor: Int,
    val idsucursal: Int,
    val numero: Int,
    val audfecharegistro: Date,
    val audfechamodifico: Date,
    val costototal: Float
)