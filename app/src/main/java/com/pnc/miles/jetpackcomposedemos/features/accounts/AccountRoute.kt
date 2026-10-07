package com.pnc.miles.jetpackcomposedemos.features.accounts

import kotlinx.serialization.Serializable

sealed interface AccountPlannerRoute

@Serializable
data object AccountRoute: AccountPlannerRoute

@Serializable
data class AccountDetailsRoute(
    val id: String
): AccountPlannerRoute