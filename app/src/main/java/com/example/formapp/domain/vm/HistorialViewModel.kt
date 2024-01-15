package com.example.formapp.domain.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.formapp.domain.model.Historial
import com.example.formapp.domain.repository.HistorialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.formapp.data.Result
import kotlinx.coroutines.flow.asStateFlow

import java.util.*
import javax.inject.Inject




@HiltViewModel
class HistorialViewModel @Inject constructor(private val historialRepository: HistorialRepository) : ViewModel()  {

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()


    fun insertarHistorial(origen: String, destino: String, distanciaKilometros: Int, tiempoHoras: Double, fecha: String) {
        val historial = Historial(
            origen = origen,
            destino = destino,
            kilometers = distanciaKilometros,
            tiempo = tiempoHoras,
            fecha = fecha
        )

        viewModelScope.launch {
            val result = historialRepository.insertHistorial(historial)
            // Puedes manejar el resultado aquí, por ejemplo, mostrando un mensaje de éxito o error
        }
    }


    private val _historialList = MutableStateFlow<Result<List<Historial>>>(Result.Loading as Result<List<Historial>>)
    val historialList: StateFlow<Result<List<Historial>>> = _historialList

    fun getHistorial() {
        viewModelScope.launch {
            try {
                val result = historialRepository.getHistorial()
                _historialList.value = result
            } catch (e: Exception) {
                _historialList.value = Result.Error()
            } finally {
                _loading.value = false
            }
        }
    }
}