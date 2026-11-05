package com.pnc.miles.jetpackcomposedemos.features.authentication.data.storage

import com.pnc.miles.jetpackcomposedemos.features.authentication.domain.User

class FakeTokenStore(
    var saved: User? = null
): TokenStore {
    var clearCalls = 0

    override suspend fun load(): User? = saved

    override suspend fun save(user: User) {
        saved = user
    }

    override suspend fun clear() {
        clearCalls++
        saved = null
    }
}