package com.pnc.miles.jetpackcomposedemos.features.artists.domain

import javax.inject.Inject

class RefreshArtistUseCase @Inject constructor(
    private val repository: ArtistRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.refreshArtist(id)
    }
}