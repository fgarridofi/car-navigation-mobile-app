package com.example.formapp.ui.form.Login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.formapp.data.Result
import com.example.formapp.domain.use_cases.Login.LoginUseCase
import com.example.formapp.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase, @ApplicationContext val context: Context,

                                         ) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private val eventChannel = Channel<FormViewModel.UiEvent>()
    val eventFlow = eventChannel.receiveAsFlow()


    var job: Job? = null

    fun getUserLogin( email: String, password: String) {

        if (email.trim().isEmpty() && password.trim().isEmpty()
        ) {

            _state.value =
                LoginState(error = "Values can't be empty!", isLoading = false)

            return
        }

        job?.cancel()

        job = viewModelScope.launch(Dispatchers.IO) {

            loginUseCase(
                email = email,
                password = password
            ).also { result ->

                when (result) {

                    is Result.Error -> {

                        Log.e("Error de verivicacion","Error de verificao")
                        eventChannel.send(
                            FormViewModel.UiEvent.ShowSnackBar(
                                message = result.message
                            )
                        )
                    }
                    is Result.Success -> {


                        _state.value = state.value.copy(
                            isLoading = false,
                            internet = false,
                            success = 1
                        )

                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: UiText?): UiEvent()
    }

    fun clearViewModel() {

        state.value.internet = false
        state.value.isLoading = false
        state.value.success = -1
        //state.value.loginList = emptyList()
        state.value.error = ""

    }

}

