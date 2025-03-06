package com.titixoid.taskmanager.ui.admin.worker_create

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titixoid.domain.models.User
import com.titixoid.domain.usecases.CreateWorkerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Immutable
data class CreateWorkerUiState(
    val login: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean
        get() = firstName.isNotEmpty() && lastName.isNotEmpty() &&
                login.isNotEmpty() && password.isNotEmpty() &&
                email.isNotEmpty()
}

class CreateWorkerViewModel(
    private val createWorkerUseCase: CreateWorkerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateWorkerUiState())
    val uiState = _uiState.asStateFlow()

    fun onLoginChange(login: String) {
        _uiState.value = _uiState.value.copy(login = login)
    }

    fun onFirstNameChange(firstName: String) {
        _uiState.value = _uiState.value.copy(firstName = firstName)
    }

    fun onLastNameChange(lastName: String) {
        _uiState.value = _uiState.value.copy(lastName = lastName)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun createWorker(onResult: (Boolean) -> Unit) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            try {
                val newWorker = User(
                    id = "",
                    login = uiState.value.login,
                    firstName = uiState.value.firstName,
                    lastName = uiState.value.lastName,
                    password = uiState.value.password,
                    email = uiState.value.email,
                    role = "worker",
                    taskCount = 0,
                )
                val result = createWorkerUseCase(newWorker)
                _uiState.value = _uiState.value.copy(isLoading = false)
                onResult(result)
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Ошибка при создании рабочего"
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = errorMessage
                )
                onResult(false)
            }
        }
    }
}