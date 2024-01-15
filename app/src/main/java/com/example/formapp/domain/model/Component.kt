package com.example.formapp.domain.model

import java.util.*

data class Component (

    val id : String = UUID.randomUUID().toString(),
    val name: String,
    val brand : String,
    var kilometers: Int,
    var lifespan: Int,
    val type: String,
    )