package com.pnc.miles.jetpackcomposedemos.features.artist.presentation.state

import com.pnc.miles.jetpackcomposedemos.features.artists.presentation.state.ArtistDirectoryState
import com.pnc.miles.jetpackcomposedemos.testutil.builders.anArtist
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ArtistDirectoryStateFilterTest(
    private val genreFilter: String,
    private val locationFilter: String,
    private val expectedIds: List<Int>
) {
    @Test
    fun `displayedArtists returns only the artists matching both filters`() {
        val state = ArtistDirectoryState(
            artists = artists,
            genreFilter = genreFilter,
            locationFilter = locationFilter
        )

        assertEquals(expectedIds, state.displayedArtists.map { it.id })
    }

    // A companion class or object can access the private members of its companion. Use a companion
    // object for methods and values which are not specific to instances of the companion class.
    companion object {
        // fixture that is shared by every case
        private val artists = listOf(
            anArtist(id = 1, genre = "Rock", location = "New York, NY"),
            anArtist(id = 2, genre = "Pop", location = "Miami"),
            anArtist(id = 3, genre = "Rock", location = "Chicago"),
            anArtist(id = 4, genre = "Easy Listening", location = "New York, NY")
        )

        @JvmStatic
        @Parameterized.Parameters(name = "genre=''{0}'', location=''{1}'' -> {2}")
        fun cases(): List<Array<Any>> = listOf(
            // --- no filtering ---
            arrayOf("", "", listOf(1, 2, 3, 4)),

            // --- genre only filter ---
            arrayOf("Easy Listening", "", listOf(4)),
            arrayOf("Rock", "", listOf(1, 3)),
            arrayOf("rock", "", listOf(1, 3)),      // case-insensitivity
            arrayOf("ROCK", "", listOf(1, 3)),
            arrayOf("oc", "", listOf(1, 3)),        // substring matches

            // --- location only ---
            arrayOf("", "new york", listOf(1, 4)),  // case-insensitive
            arrayOf("", "NY", listOf(1, 4)),        // matches the state abbreviation
            arrayOf("", "Chicago", listOf(3)),

            // --- both filters must match (AND, not OR) ---
            arrayOf("Rock", "New York", listOf(1)),
            arrayOf("Rock", "Miami", emptyList<Int>()),
            arrayOf("Pop", "Chicago", emptyList<Int>()),

            // --- no results ---
            arrayOf("Jazz", "", emptyList<Int>()),
            arrayOf("", "Tokyo", emptyList<Int>()),

            // --- whitespace: the filter text is not trimmed ---
            arrayOf(" Rock", "", emptyList<Int>()),
            arrayOf("Rock ", "", emptyList<Int>()),
        )
    }
}