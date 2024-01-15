package com.example.formapp.ui.screens

import com.example.formapp.domain.model.Component

data class CardInfo(
    val id : String? = null,
    val name: String,
    val brand : String,
    val kilometers: Float,
    val lifespan: Float,
    val component: Component


) {
    val percentage: Float
        get() = (1-(kilometers / lifespan)) * 100

    val km_restantes: Float
        get() = lifespan - kilometers
}