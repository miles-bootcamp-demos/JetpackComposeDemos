package com.pnc.miles.jetpackcomposedemos.core

import com.pnc.miles.jetpackcomposedemos.features.authentication.data.storage.DataStoreTokenStore
import com.pnc.miles.jetpackcomposedemos.features.authentication.data.storage.TokenStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenStoreModule {
    @Binds
    abstract fun bindTokenStore(implementation: DataStoreTokenStore): TokenStore
}