package com.pnc.miles.jetpackcomposedemos.testutil.builders

import com.pnc.miles.jetpackcomposedemos.features.artists.data.ArtistDto

fun anArtistDto(
    id: Int = 1,
    name: String = "Test Artist",
    genre: String = "Rock",
    location: String = "New York, NY",
    imageUrl: String = "",
    description: String = "A test artist",
    tags: String = ""
) = ArtistDto(id, name, genre, location, imageUrl, description, tags)