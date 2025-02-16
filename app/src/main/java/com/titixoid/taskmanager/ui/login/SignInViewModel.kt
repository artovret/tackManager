package com.titixoid.taskmanager.ui.login

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Immutable
data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val error: String? = null
)


class SignInViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email,
            )
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun validateCredentials() {
        val emailValid = uiState.value.email.isNotBlank() && isValidEmail(uiState.value.email)
        val passwordValid = uiState.value.password.isNotBlank()

        _uiState.update { it.copy(error = if (emailValid && passwordValid) null else "Некорректные данные") }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}