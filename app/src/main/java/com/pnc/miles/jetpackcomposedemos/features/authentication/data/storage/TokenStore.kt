package com.pnc.miles.jetpackcomposedemos.features.authentication.data.storage

import com.pnc.miles.jetpackcomposedemos.features.authentication.domain.User

interface TokenStore {
    suspend fun load(): User?
    suspend fun save(user: User)
    suspend fun clear()
}