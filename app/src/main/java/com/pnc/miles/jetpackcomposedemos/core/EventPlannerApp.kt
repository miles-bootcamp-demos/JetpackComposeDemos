package com.pnc.miles.jetpackcomposedemos.core

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.window.core.layout.WindowSizeClass
import com.pnc.miles.jetpackcomposedemos.BuildConfig
import com.pnc.miles.jetpackcomposedemos.features.artists.domain.Artist
import com.pnc.miles.jetpackcomposedemos.features.artists.presentation.composables.ArtistDetails
import com.pnc.miles.jetpackcomposedemos.features.artists.presentation.composables.ArtistDirectoryRoute
import com.pnc.miles.jetpackcomposedemos.features.authentication.domain.AuthState
import com.pnc.miles.jetpackcomposedemos.features.authentication.presentation.AuthViewModel
import com.pnc.miles.jetpackcomposedemos.features.authentication.presentation.LoginScreen
import com.pnc.miles.jetpackcomposedemos.features.authentication.presentation.SignInScreen
import com.pnc.miles.jetpackcomposedemos.features.boardmembers.domain.BoardMember
import com.pnc.miles.jetpackcomposedemos.features.boardmembers.presentation.BoardMemberDetails
import com.pnc.miles.jetpackcomposedemos.features.boardmembers.presentation.BoardMemberList
import com.pnc.miles.jetpackcomposedemos.features.orders.presentation.OrdersScreen
import com.pnc.miles.jetpackcomposedemos.features.todo.presentation.ToDosScreen
import com.pnc.miles.jetpackcomposedemos.legacy.LegacyArtistList

@Composable
fun EventPlannerApp(
    viewModel: AuthViewModel = hiltViewModel()
) {
    Log.d("EventPlannerApp", "Base URL = ${BuildConfig.BASE_URL}")
    val authState by viewModel.uiState.collectAsState()

    val windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
    val useTwoPaneLayout = windowSizeClass.isWidthAtLeastBreakpoint(
        WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND
    )

    val navController = rememberNavController()
    val boardMembers = BoardMember.getBoardMembers()

    when (authState) {
        is AuthState.Restoring -> CircularProgressIndicator()
        is AuthState.Unauthenticated -> {
//            LoginScreen(
//                onLoginSuccess = { user ->
//                    viewModel.login(user)
//                }
//            )
            SignInScreen(
                onSignIn = { user -> viewModel.login(user) }
            )
        }
        is AuthState.Authenticated -> {
            NavHost(
                navController = navController,
                startDestination = DashboardRoute
            ) {
                composable<DashboardRoute> {
                    EventPlanningDashboard(
                        onViewArtists = {
                            navController.navigate(ArtistListRoute)
                        },
                        onViewBoardMembers = {
                            navController.navigate(BoardMemberListRoute)
                        },
                        onViewOrders = {
                            navController.navigate(OrdersRoute)
                        },
                        onViewToDos = {
                            navController.navigate(ToDosRoute)
                        },
                        onSignOut = {
                            viewModel.logout()
                        }
//                        onLegacyArtists = {
//                            navController.navigate(LegacyArtistListRoute)
//                        }
                    )
                }

                composable<ArtistListRoute> {
                    ArtistDirectoryRoute(
                        onArtistSelected = { artistId ->
                            navController.navigate(ArtistDetailsRoute(artistId))
                        },
                        onBack = {
                            navController.popBackStack()
                        },
                        useTwoPaneLayout = useTwoPaneLayout
                    )
                }

                composable<BoardMemberListRoute> {
                    BoardMemberList(
                        members = boardMembers,
                        onMemberSelected = { memberId ->
                            navController.navigate(BoardMemberDetailsRoute(memberId))
                        },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable<ArtistDetailsRoute> { backStackEntry ->
                    val route = backStackEntry.toRoute<ArtistDetailsRoute>()
                    val artistId = route.artistId

                    ArtistDetails(artistId = artistId)
                }

                composable<BoardMemberDetailsRoute> { backStackEntry ->
                    val route = backStackEntry.toRoute<BoardMemberDetailsRoute>()
                    val memberId = route.boardMemberId
                    val member = boardMembers.find { it.id == memberId }

                    if (member != null) {
                        BoardMemberDetails(boardMember = member)
                    }
                    else {
                        Text("Board member not found")
                        // TODO: create a NotFound composable
                    }
                }

                composable<OrdersRoute> {
                    OrdersScreen(useTwoPaneLayout = useTwoPaneLayout)
                }

                composable<ToDosRoute> {
                    ToDosScreen(useTwoPaneLayout = useTwoPaneLayout)
                }

//                composable<LegacyArtistListRoute> {
//                    LegacyArtistList(
//                        artists = artists,
//                        onArtistSelected = { artistId ->
//                            navController.navigate(ArtistDetailsRoute(artistId))
//                        }
//                    )
//                }
            }
        }
    }
}