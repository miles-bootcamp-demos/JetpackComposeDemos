package com.pnc.miles.jetpackcomposedemos.features.artists.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnc.miles.jetpackcomposedemos.features.artists.domain.AsyncRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class AsyncViewModel(
    private val repository: AsyncRepository = AsyncRepository()
): ViewModel() {
    fun loadArtists() {
        viewModelScope.launch {
//            Thread.sleep(10_000)
            delay(10.seconds)
            repository.getArtists()
        }
    }
}