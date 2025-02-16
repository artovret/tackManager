package com.titixoid.taskmanager.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _authenticated = MutableStateFlow(false)
    private val _admin = MutableStateFlow(true)
    val authenticated: StateFlow<Boolean> = _authenticated
    val admin: StateFlow<Boolean> = _admin
}