package com.pnc.miles.jetpackcomposedemos.di

import com.pnc.miles.jetpackcomposedemos.core.NetworkModule
import com.pnc.miles.jetpackcomposedemos.features.artists.data.ArtistDataSource
import com.pnc.miles.jetpackcomposedemos.features.orders.data.OrderDataSource
import com.pnc.miles.jetpackcomposedemos.testutil.fakes.FakeArtistDataSource
import com.pnc.miles.jetpackcomposedemos.testutil.fakes.FakeOrderDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
abstract class TestNetworkModule {
    @Binds
    abstract fun bindArtistDataSource(fake: FakeArtistDataSource): ArtistDataSource

    @Binds
    abstract fun bindOrderDataSource(fake: FakeOrderDataSource): OrderDataSource
}