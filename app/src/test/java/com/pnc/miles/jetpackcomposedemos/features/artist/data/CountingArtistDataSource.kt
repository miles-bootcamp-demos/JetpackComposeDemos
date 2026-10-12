package com.pnc.miles.jetpackcomposedemos.features.artist.data

import com.pnc.miles.jetpackcomposedemos.features.artists.data.ArtistDataSource
import com.pnc.miles.jetpackcomposedemos.features.artists.data.ArtistDto

class CountingArtistDataSource: ArtistDataSource {
    var calls = 0
        private set

    override suspend fun getArtists(): List<ArtistDto> {
        calls++
        return emptyList()
    }

    override suspend fun getArtist(id: Int): ArtistDto = error("not used in these tests")
}