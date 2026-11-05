package com.pnc.miles.jetpackcomposedemos.features.authentication.domain

import android.se.omapi.Session
import com.pnc.miles.jetpackcomposedemos.features.authentication.data.storage.FakeTokenStore
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Test

class SessionManagerTest {
    private val user = User(
        id = "110000",
        username = "Test User",
        token = "t"
    )

    @Test
    fun `starts out unauthenticated`() {
        val session = SessionManager(
            FakeTokenStore(),
            CoroutineScope(Dispatchers.Unconfined)
        )

        assertEquals(AuthState.Restoring, session.state.value)
        assertNull(session.currentUser)
    }

    @Test
    fun `signing in exposes the user`() {
        val session = SessionManager(
            FakeTokenStore(),
            CoroutineScope(Dispatchers.Unconfined)
        )

        session.signIn(user)

        assertEquals(AuthState.Authenticated(user), session.state.value)
        assertEquals(user, session.currentUser)
    }

    @Test
    fun `signing out clears the user`() {
        val session = SessionManager(
            FakeTokenStore(),
            CoroutineScope(Dispatchers.Unconfined)
        )
        session.signIn(user)

        session.signOut()

        assertEquals(AuthState.Unauthenticated, session.state.value)
        assertNull(session.currentUser)
    }
}