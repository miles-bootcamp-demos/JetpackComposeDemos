package com.pnc.miles.jetpackcomposedemos.core

import com.pnc.miles.jetpackcomposedemos.features.artists.data.ArtistDataSource
import com.pnc.miles.jetpackcomposedemos.features.artists.data.DefaultArtistRepository
import com.pnc.miles.jetpackcomposedemos.features.artists.data.HardCodedArtistDataSource
import com.pnc.miles.jetpackcomposedemos.features.artists.data.OfflineFirstArtistRepository
import com.pnc.miles.jetpackcomposedemos.features.artists.data.RandomArtistDataSource
import com.pnc.miles.jetpackcomposedemos.features.artists.domain.ArtistRepository
import com.pnc.miles.jetpackcomposedemos.features.orders.data.DefaultOrderRepository
import com.pnc.miles.jetpackcomposedemos.features.orders.data.LocalOnlyOrderRepository
import com.pnc.miles.jetpackcomposedemos.features.orders.domain.OrderRepository
import com.pnc.miles.jetpackcomposedemos.features.todo.data.DefaultToDoRepository
import com.pnc.miles.jetpackcomposedemos.features.todo.domain.ToDoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
abstract class AppInjectionModule {
    @Binds
    abstract fun bindArtistRepository(
        implementation: OfflineFirstArtistRepository
    ): ArtistRepository

    @Binds
    abstract fun bindOrdersRepository(
        implementation: LocalOnlyOrderRepository
    ): OrderRepository

    @Binds
    abstract fun bindToDoRepository(
        implementation: DefaultToDoRepository
    ): ToDoRepository

    // Adding a companion object as the compiler doesn't
    // allow for @Provides and @Binds in the same file
    // unless you have it within a companion object
    companion object {
        @Provides
        @Singleton
        @ApplicationScope
        fun provideAppScope(): CoroutineScope {
            return CoroutineScope(SupervisorJob() + Dispatchers.Default)
            // NOTE: using SupervisorJob keeps one failed child from
            // cancelling the scope for everyone else.
        }
    }
}