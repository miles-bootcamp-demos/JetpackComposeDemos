package com.pnc.miles.jetpackcomposedemos

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pnc.miles.jetpackcomposedemos.core.EventPlannerApp
import com.pnc.miles.jetpackcomposedemos.features.authentication.data.oidc.AuthorizeUrl
import com.pnc.miles.jetpackcomposedemos.features.authentication.data.oidc.OidcRedirects
import com.pnc.miles.jetpackcomposedemos.features.authentication.data.oidc.Pkce
import com.pnc.miles.jetpackcomposedemos.ui.theme.KazooTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class EventPlannerApplication: Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var oidcRedirects: OidcRedirects

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
            KazooTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)

                    Box(
                        modifier = modifier
                    ) {
                        EventPlannerApp()
                        // AccountApp()
                    }
                }
            }
        }
    }
}