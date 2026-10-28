package com.pnc.miles.jetpackcomposedemos.core

import com.pnc.miles.jetpackcomposedemos.BuildConfig
import com.pnc.miles.jetpackcomposedemos.features.authentication.domain.SessionManager
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

// An AuthInterceptor (Authentication Interceptor) is a client-side middleware component in web and
// mobile applications that automatically inspects, modifies, and handles outgoing HTTP requests
// and incoming HTTP responses.
//
// Automatically attaches authentication credentials (such as JWT Bearer tokens) to outgoing
// network calls and catches global authentication errors (like 401 Unauthorized).
//
// When the server replies, the interceptor can inspect the status code. If it detects an expired
// token (401 Unauthorized), it can trigger a background token refresh request and retry the
// original call seamlessly without bothering the user.
@Singleton
class AuthInterceptor @Inject constructor(
    private val session: SessionManager
): Interceptor {
    // get the hostname of the AW api
    private val apiHost = BuildConfig.AW_BASE_URL.toHttpUrl().host

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = session.currentUser?.token

        // if there is no token, or if the request is to a different host
        // then we do NOT need or want to send a token
        if (token == null || request.url.host != apiHost) {
            return chain.proceed(request)
        }

        // we need to add our token to the request
        return chain.proceed(
            request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        )
    }
}