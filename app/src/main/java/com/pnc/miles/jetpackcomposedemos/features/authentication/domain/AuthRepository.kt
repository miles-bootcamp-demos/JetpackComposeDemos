package com.pnc.miles.jetpackcomposedemos.features.authentication.domain

interface AuthRepository {
    suspend fun login(userId: String, passcode: String): Result<User>
}