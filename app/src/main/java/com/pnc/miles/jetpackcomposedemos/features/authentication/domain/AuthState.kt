package com.pnc.miles.jetpackcomposedemos.features.authentication.domain

sealed interface AuthState {
    data class Authenticated(val user: User): AuthState
    data object Unauthenticated: AuthState
}