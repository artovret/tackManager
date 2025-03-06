package com.titixoid.taskmanager.ui.login

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
    val role: String? = null,
    val userId: String? = null,
    val error: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean? = null
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

    fun signIn() {
        _uiState.update { it.copy(isLoading = true, error = false) }
        viewModelScope.launch {
            val user = signInUseCase(uiState.value.login, uiState.value.password)
            _uiState.update {
                it.copy(
                    isSuccess = (user != null),
                    role = user?.role,
                    userId = user?.id,
                    isLoading = false,
                    error = (user == null)
                )
            }
        }
    }

    fun resetAuthState() {
        _uiState.update { it.copy(isLoading = false, error = false) }
    }
}