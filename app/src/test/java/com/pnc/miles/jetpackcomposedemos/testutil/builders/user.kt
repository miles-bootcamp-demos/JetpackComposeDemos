package com.pnc.miles.jetpackcomposedemos.testutil.builders

import com.pnc.miles.jetpackcomposedemos.features.authentication.domain.User

fun aUser(
    id: String = "user-1",
    username: String = "testuser",
    token: String = "test-token"
) = User(id, username, token)