package com.pnc.miles.jetpackcomposedemos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun ArtistList(
    artists: List<Artist>,
    onArtistSelected: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        artists.forEach { artist ->
            ArtistCard(
                artist = artist,
                onClick = {
                    onArtistSelected(artist.id)
                }
            )
        }
    }
}