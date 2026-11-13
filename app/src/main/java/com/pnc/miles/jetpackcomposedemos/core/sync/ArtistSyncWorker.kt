package com.pnc.miles.jetpackcomposedemos.core.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pnc.miles.jetpackcomposedemos.features.artists.domain.SyncArtistsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

@HiltWorker
class ArtistSyncWorker @AssistedInject constructor(
    // @Assisted values come from WorkManager
    // everything else comes from Hilt
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val syncArtists: SyncArtistsUseCase,
    private val notifier: ArtistSyncNotifier
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            val newArtists = syncArtists()
            if (newArtists > 0) {
                notifier.notifyNewArtists(newArtists)
            }
            Result.success()
        } catch (e: CancellationException) {
            throw e
        }
        catch (e: IOException) {
            // no network, or the server was unreachable
            Result.retry()
        }
        catch (e: Exception) {
            Result.failure()
        }
    }
}