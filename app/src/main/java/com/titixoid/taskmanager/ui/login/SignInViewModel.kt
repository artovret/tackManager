package com.titixoid.taskmanager.ui.login

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titixoid.domain.usecases.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Immutable
data class SignInUiState(
    val login: String = "",
    val password: String = "",
    val error: Boolean = false,
    val isLoading: Boolean = false
)

class SignInViewModel(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    fun updateEmail(login: String) {
        _uiState.update { it.copy(login = login, error = false) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password, error = false) }
    }

    fun signIn(onResult: (Boolean) -> Unit) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val emailValid = uiState.value.login.isNotBlank() &&
                    android.util.Patterns.EMAIL_ADDRESS.matcher(uiState.value.login).matches()
            val passwordValid = uiState.value.password.isNotBlank()

            if (!emailValid || !passwordValid) {
                _uiState.update { it.copy(error = true, isLoading = false) }
                onResult(false)
                return@launch
            }

            Log.d("SignInViewModel", "Электронная почта: ${uiState.value.login}")
            Log.d("SignInViewModel", "Пароль: ${uiState.value.password}")

            val success = signInUseCase(uiState.value.login, uiState.value.password)
            Log.d("SignInViewModel", "Результат UseCase: $success")

            if (success) {
                _uiState.update { it.copy(error = false, isLoading = false) }
            } else {
                _uiState.update { it.copy(error = true, isLoading = false) }
            }
            onResult(success)
        }
    }
}