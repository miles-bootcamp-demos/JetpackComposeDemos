package com.pnc.miles.jetpackcomposedemos.features.artists.data

interface ArtistDataSource {
    suspend fun getArtists(): List<ArtistDto>
    suspend fun getArtist(id: Int): ArtistDto?
}