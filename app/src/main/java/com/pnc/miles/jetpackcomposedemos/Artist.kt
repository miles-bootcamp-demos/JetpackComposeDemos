package com.pnc.miles.jetpackcomposedemos

data class Artist(
    val id: Int,
    val name: String,
    val genre: String,
    val location: String,
    val imageUrl: String,
    val description: String,
    val tags: String
) {
    // We would like a static function to genereate some fake artist data
    // but Kotlin does not have "static" keyword to create class-level functions.
    // Instead, we do it using a "companion object"
    companion object {
        fun generateArtists(num: Int): List<Artist> = List(num) { index ->
            Artist(
                id = index + 1,
                name = "Artist ${index + 1}",
                genre = listOf("Rock", "Pop", "Jazz", "Acoustic", "Classical").random(),
                location = listOf("New York", "Los Angeles", "Chicago", "Houston", "Miami").random(),
                imageUrl = "/images/fakeimage.jpg",
                description = "Some fake details",
                tags = "tag,tag,tag"
            )
        }
    }
}

