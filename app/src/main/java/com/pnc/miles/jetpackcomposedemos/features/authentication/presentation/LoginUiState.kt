package com.pnc.miles.jetpackcomposedemos.features.authentication.presentation

import com.pnc.miles.jetpackcomposedemos.features.authentication.domain.User

sealed interface LoginUiState {
    data object Idle: LoginUiState
    data object Loading: LoginUiState
    data class Success(val user: User): LoginUiState
    data class Error(val message: String): LoginUiState
}