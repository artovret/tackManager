package com.titixoid.taskmanager.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titixoid.domain.usecases.CheckAuthStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

sealed class StartNavigationDestination {
    data object Loading : StartNavigationDestination()
    data object Auth : StartNavigationDestination()
    data object Admin : StartNavigationDestination()
    data class Worker(val workerId: String) : StartNavigationDestination()
}
class StartScreenViewModel(
    private val checkAuthStatusUseCase: CheckAuthStatusUseCase
) : ViewModel() {

    private val _navigationState =
        MutableStateFlow<StartNavigationDestination>(StartNavigationDestination.Loading)
    val navigationState: StateFlow<StartNavigationDestination> get() = _navigationState

    init {
        viewModelScope.launch {
            val authStatus = checkAuthStatusUseCase()
            val destination = if (!authStatus.isAuthenticated) {
                StartNavigationDestination.Auth
            } else {
                when (authStatus.role?.lowercase(Locale.getDefault())) {
                    "admin" -> StartNavigationDestination.Admin
                    else -> StartNavigationDestination.Worker(authStatus.userId ?: "")
                }
            }
            _navigationState.value = destination
        }
    }
}