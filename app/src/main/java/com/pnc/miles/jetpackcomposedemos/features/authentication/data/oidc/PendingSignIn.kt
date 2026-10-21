package com.pnc.miles.jetpackcomposedemos.features.authentication.data.oidc

data class PendingSignIn (
    val verifier: String,
    val state: String,
    val nonce: String
)