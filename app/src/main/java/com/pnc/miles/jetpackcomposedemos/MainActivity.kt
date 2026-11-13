package com.pnc.miles.jetpackcomposedemos

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.Configuration
import com.pnc.miles.jetpackcomposedemos.core.settings.LocalAppSettings
import com.pnc.miles.jetpackcomposedemos.core.structure.EventPlannerApp
import com.pnc.miles.jetpackcomposedemos.core.settings.SettingsViewModel
import com.pnc.miles.jetpackcomposedemos.core.settings.ThemeMode
import com.pnc.miles.jetpackcomposedemos.features.authentication.data.oidc.OidcRedirects
import com.pnc.miles.jetpackcomposedemos.ui.theme.KazooTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class EventPlannerApplication: Application(), Configuration.Provider {
    // Hilt's worker factory lets a worker's constructor ask for
    // dependencies (such as repositories) to be injected
    // the same way a view model can
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var oidcRedirects: OidcRedirects

    private val settingsViewModel: SettingsViewModel by viewModels()

    private companion object {
        val LIGHT_SCRIM = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
        val DARK_SCRIM = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val uri = intent.dataString
        if (uri != null && uri.startsWith(BuildConfig.OIDC_REDIRECT_URI)) {
            oidcRedirects.deliver(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        val verifier = Pkce.newCodeVerifier()
//        Log.d("PKCE", AuthorizeUrl.build(
//            BuildConfig.AUTH_BASE_URL,
//            "adventureworks-mobile",
//            BuildConfig.OIDC_REDIRECT_URI,
//            BuildConfig.OIDC_SCOPES,
//            Pkce.newRandomValue(),
//            Pkce.newRandomValue(),
//            Pkce.codeChallenge(verifier)
//        ).toString())

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val settings by settingsViewModel.settings.collectAsStateWithLifecycle()
            val darkTheme = when (settings.themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }
            // enableEdgeToEdge() picked the status and navigation bar icon colors
            // from the SYSTEM them when the activity was created
            // Now that our app's own setting might disagree with the system
            // set them again whenever the chosen them changes
            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        lightScrim = android.graphics.Color.TRANSPARENT,
                        darkScrim = android.graphics.Color.TRANSPARENT
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim = LIGHT_SCRIM,
                        darkScrim = DARK_SCRIM
                    ) { darkTheme }
                )
                onDispose {}
            }
            CompositionLocalProvider(LocalAppSettings provides settings) {
                KazooTheme(darkTheme = darkTheme) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        EventPlannerApp()
                    }
                }
            }
        }
    }
}