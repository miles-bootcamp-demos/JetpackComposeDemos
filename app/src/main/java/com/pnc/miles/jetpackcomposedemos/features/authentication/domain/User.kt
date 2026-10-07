package com.pnc.miles.jetpackcomposedemos.features.authentication.domain

data class User(
    val id: String,
    val username: String,
    val token: String
)