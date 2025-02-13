package com.titixoid.taskmanager.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


sealed class StartScreenEvent {
    object NavigateToHome : StartScreenEvent()
    object NavigateToAuth : StartScreenEvent()
}

/**
 * ViewModel для стартового экрана сделана про запас, возможно не понадобится в будущем.
 */
class StartScreenViewModel : ViewModel() {

    private val _uiEvent = MutableSharedFlow<StartScreenEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            val isAuthorized = checkAuthStatus()
            if (isAuthorized) {
                _uiEvent.emit(StartScreenEvent.NavigateToHome)
            } else {
                _uiEvent.emit(StartScreenEvent.NavigateToAuth)
            }
        }
    }

    /**
     * Функция-эмулятор проверки авторизации пользователя.
     * Здесь можно реализовать логику проверки токена или выполнить запрос к серверу.
     */
    private suspend fun checkAuthStatus(): Boolean {
        return false
    }
} 