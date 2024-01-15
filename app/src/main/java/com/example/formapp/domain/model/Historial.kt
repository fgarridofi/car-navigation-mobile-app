package com.example.formapp.domain.model

import java.util.*

data class Historial(

    val origen: String,
    val destino : String,
    val kilometers: Int,
    val tiempo: Double,
    val fecha: String

    )