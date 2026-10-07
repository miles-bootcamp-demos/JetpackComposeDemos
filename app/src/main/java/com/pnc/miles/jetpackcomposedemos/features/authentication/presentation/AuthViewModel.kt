package com.pnc.miles.jetpackcomposedemos.features.authentication.presentation

import androidx.lifecycle.ViewModel
import com.pnc.miles.jetpackcomposedemos.features.authentication.domain.AuthState
import com.pnc.miles.jetpackcomposedemos.features.authentication.domain.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    fun login(user: User) {
        _uiState.update {
            AuthState.Authenticated(user)
        }
    }

    fun logout() {
        _uiState.update {
            AuthState.Unauthenticated
        }
    }
}