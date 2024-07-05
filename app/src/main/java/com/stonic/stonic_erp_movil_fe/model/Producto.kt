package com.stonic.stonic_erp_movil_fe.model

data class Producto(
    val idproducto: Int,
    val nombre: String,
    val audfecharegistro: String,
    val audfechamodifico: String,
    val codigo: String,
    val idproductora: Int,
    val contenido: String,
    val idmedida: Int,
    val urlimagen: String,
    val descripcion: String
)