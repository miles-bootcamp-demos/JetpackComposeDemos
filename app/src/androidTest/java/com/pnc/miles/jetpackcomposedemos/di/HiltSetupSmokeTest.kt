package com.pnc.miles.jetpackcomposedemos.di

import com.pnc.miles.jetpackcomposedemos.core.room.AppDatabase
import com.pnc.miles.jetpackcomposedemos.features.artists.data.ArtistDataSource
import com.pnc.miles.jetpackcomposedemos.features.artists.domain.ArtistRepository
import com.pnc.miles.jetpackcomposedemos.features.orders.data.OrderDataSource
import com.pnc.miles.jetpackcomposedemos.testutil.fakes.FakeArtistDataSource
import com.pnc.miles.jetpackcomposedemos.testutil.fakes.FakeOrderDataSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class HiltSetupSmokeTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject lateinit var artistDataSource: ArtistDataSource
    @Inject lateinit var orderDataSource: OrderDataSource
    @Inject lateinit var artistRepository: ArtistRepository
    @Inject lateinit var database: AppDatabase

    @Before
    fun setUp() {
        hiltRule.inject()   // fills in the @Inject fields above
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun givenTestModules_whenInjected_thenTheNetworkEdgesAreFakes() {
        assertTrue(artistDataSource is FakeArtistDataSource)
        assertTrue(orderDataSource is FakeOrderDataSource)
    }

    @Test
    fun givenTestModules_whenInjected_thenTheDatabaseIsInMemory() {
        // A file-backed database has a name; an in-memory one has none
        assertNull(database.openHelper.databaseName)
    }
}