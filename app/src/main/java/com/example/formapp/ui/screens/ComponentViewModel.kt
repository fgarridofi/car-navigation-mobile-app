package com.example.formapp.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.formapp.domain.model.Component
import com.example.formapp.domain.repository.ComponentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.formapp.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ComponentViewModel @Inject constructor (private val componentRepository: ComponentRepository) : ViewModel() {

    private val _components = MutableStateFlow<Result<List<Component>>>(Result.Loading as Result<List<Component>>)
    val components: StateFlow<Result<List<Component>>> = _components

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun setLoading(isLoading: Boolean) {
        _loading.value = isLoading
    }

    fun getComponents() {
        viewModelScope.launch {
            _loading.value = true // Añadir esta línea
            val result = componentRepository.getComponets()
            _components.value = result
            _loading.value = false // Añadir esta línea
        }
    }
    val createLoading = MutableStateFlow(false)

    fun createComponent(component: Component) {
        viewModelScope.launch {
            createLoading.value = true
            componentRepository.insertComponent(component)
            getComponents()
            createLoading.value = false
        }
    }

    fun updateComponent(id: String, component: Component) {
        viewModelScope.launch {
            componentRepository.updateComponent(id, component)
            getComponents()
        }
    }

    fun deleteComponent(id: String) {
        viewModelScope.launch {
            componentRepository.deleteComponent(id)
            getComponents()
        }
    }

    fun updateComponentKilometers(id: String, newKilometers: Int) {
        viewModelScope.launch {
            val result = componentRepository.getComponets()
            if (result is Result.Success) {
                val components = result.data
                if (components != null) {
                    components.forEach { component ->
                        if (component.id == id) {
                            val updatedComponent = component.copy(kilometers =  newKilometers)
                            componentRepository.updateComponent(id, updatedComponent)
                        }
                    }
                }
            }
        }
    }

}