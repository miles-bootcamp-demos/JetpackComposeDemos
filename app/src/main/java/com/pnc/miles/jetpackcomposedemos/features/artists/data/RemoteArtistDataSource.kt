package com.pnc.miles.jetpackcomposedemos.features.artists.data

import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteArtistDataSource: ArtistDataSource {
    @GET("talent")
    override suspend fun getArtists(): List<ArtistDto>

    @GET("talent/{id}")
    override suspend fun getArtist(
        @Path("id") id: Int
    ): ArtistDto
}