package com.pnc.miles.jetpackcomposedemos.core

import com.pnc.miles.jetpackcomposedemos.BuildConfig
import com.pnc.miles.jetpackcomposedemos.features.artists.data.ArtistDataSource
import com.pnc.miles.jetpackcomposedemos.features.artists.data.RemoteArtistDataSource
import com.pnc.miles.jetpackcomposedemos.features.orders.data.MyOrdersDataSource
import com.pnc.miles.jetpackcomposedemos.features.orders.data.OrderDataSource
import com.pnc.miles.jetpackcomposedemos.features.orders.data.RemoteMyOrdersDataSource
import com.pnc.miles.jetpackcomposedemos.features.orders.data.RemoteOrderDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.processor.internal.aggregateddeps.AggregatedDeps
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AdventureWorks

// @Module defines how to create dependencies that cannot be constructor-injected, while @InstallIn
// dictates which Android component (and lifecycle) those dependencies belong to.
// Together, they tell Hilt how to construct your objects and how long those objects should live
// in memory.
@Module
@InstallIn(SingletonComponent::class)       // App launch to close
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                        redactHeader("Authorization")
                    })
                }
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @AdventureWorks
    fun provideAWRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AW_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideArtistDataSource(
        retrofit: Retrofit
    ): ArtistDataSource {
        return retrofit.create(
            RemoteArtistDataSource::class.java
        )
    }

    @Provides
    @Singleton
    fun provideOrderDataSource(
        @AdventureWorks retrofit: Retrofit
    ): OrderDataSource {
        return retrofit.create(
            RemoteOrderDataSource::class.java
        )
    }

    @Provides
    @Singleton
    fun provideMyOrderDataSource(
        @AdventureWorks retrofit: Retrofit
    ): MyOrdersDataSource {
        return retrofit.create(
            RemoteMyOrdersDataSource::class.java
        )
    }
}