package com.example.formapp.util


import com.example.formapp.domain.model.Component
import com.example.formapp.ui.screens.CardInfo

fun Component.toCardInfo(): CardInfo {
    return CardInfo(
        id = this.id,
        name = this.name,
        brand = this.brand,
        kilometers = this.kilometers.toFloat(),
        lifespan = this.lifespan.toFloat(),
        component = this
    )
}