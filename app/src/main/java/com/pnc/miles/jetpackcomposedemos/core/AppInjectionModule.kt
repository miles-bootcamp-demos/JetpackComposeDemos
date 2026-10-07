package com.pnc.miles.jetpackcomposedemos.core

import com.pnc.miles.jetpackcomposedemos.features.artists.data.ArtistDataSource
import com.pnc.miles.jetpackcomposedemos.features.artists.data.DefaultArtistRepository
import com.pnc.miles.jetpackcomposedemos.features.artists.data.HardCodedArtistDataSource
import com.pnc.miles.jetpackcomposedemos.features.artists.data.RandomArtistDataSource
import com.pnc.miles.jetpackcomposedemos.features.artists.domain.ArtistRepository
import com.pnc.miles.jetpackcomposedemos.features.orders.data.DefaultOrderRepository
import com.pnc.miles.jetpackcomposedemos.features.orders.domain.OrderRepository
import com.pnc.miles.jetpackcomposedemos.features.todo.data.DefaultToDoRepository
import com.pnc.miles.jetpackcomposedemos.features.todo.domain.ToDoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppInjectionModule {
    @Binds
    abstract fun bindArtistRepository(
        implementation: DefaultArtistRepository
    ): ArtistRepository

    @Binds
    abstract fun bindOrdersRepository(
        implementation: DefaultOrderRepository
    ): OrderRepository

    @Binds
    abstract fun bindToDoRepository(
        implementation: DefaultToDoRepository
    ): ToDoRepository
}