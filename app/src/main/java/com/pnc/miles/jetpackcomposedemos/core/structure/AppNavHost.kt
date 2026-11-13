package com.pnc.miles.jetpackcomposedemos.core.structure

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.core.util.Consumer
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.pnc.miles.jetpackcomposedemos.core.ArtistDetailsRoute
import com.pnc.miles.jetpackcomposedemos.core.ArtistListRoute
import com.pnc.miles.jetpackcomposedemos.core.BoardMemberDetailsRoute
import com.pnc.miles.jetpackcomposedemos.core.BoardMemberListRoute
import com.pnc.miles.jetpackcomposedemos.core.DashboardRoute
import com.pnc.miles.jetpackcomposedemos.core.EventPlanningDashboard
import com.pnc.miles.jetpackcomposedemos.core.OrdersRoute
import com.pnc.miles.jetpackcomposedemos.core.SettingsRoute
import com.pnc.miles.jetpackcomposedemos.core.ToDosRoute
import com.pnc.miles.jetpackcomposedemos.core.settings.SettingsScreen
import com.pnc.miles.jetpackcomposedemos.core.sync.ArtistSyncNotifier
import com.pnc.miles.jetpackcomposedemos.features.artists.presentation.composables.ArtistDetails
import com.pnc.miles.jetpackcomposedemos.features.artists.presentation.composables.ArtistDirectoryRoute
import com.pnc.miles.jetpackcomposedemos.features.boardmembers.domain.BoardMember
import com.pnc.miles.jetpackcomposedemos.features.boardmembers.presentation.BoardMemberDetails
import com.pnc.miles.jetpackcomposedemos.features.boardmembers.presentation.BoardMemberList
import com.pnc.miles.jetpackcomposedemos.features.orders.presentation.OrdersScreen
import com.pnc.miles.jetpackcomposedemos.features.orders.presentation.PagedOrdersScreen
import com.pnc.miles.jetpackcomposedemos.features.todo.presentation.ToDosScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    useTwoPaneLayout: Boolean,
    onSignOut: () -> Unit
) {
    val boardMembers = BoardMember.getBoardMembers()

    // MainActivity is a single task, so a link tapped while the app is open
    // arrives as a NEW intent
    // And our NavHost reads the original launch intent by itself, but not
    // these later ones
    val activity = LocalActivity.current as? ComponentActivity
    DisposableEffect(activity, navController) {
        val listener = Consumer<Intent> { intent -> navController.handleDeepLink(intent) }
        activity?.addOnNewIntentListener(listener)
        onDispose { activity?.removeOnNewIntentListener(listener) }
    }

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
                onSettings = {
                    navController.navigate(SettingsRoute)
                },
                onSignOut = {
                    onSignOut()
                }
//                onLegacyArtists = {
//                    navController.navigate(LegacyArtistListRoute)
//                }
            )
        }

        composable<ArtistListRoute>(
            deepLinks = listOf(
                navDeepLink<ArtistListRoute>(basePath = ArtistSyncNotifier.ARTISTS_DEEP_LINK)
            )
        ) {
            ArtistDirectoryRoute(
                onBack = {
                    navController.popBackStack()
                }
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

//        composable<ArtistDetailsRoute> { backStackEntry ->
//            val route = backStackEntry.toRoute<ArtistDetailsRoute>()
//            val artistId = route.artistId
//
//            ArtistDetails(artistId = artistId)
//        }

        composable<BoardMemberDetailsRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<BoardMemberDetailsRoute>()
            val memberId = route.boardMemberId
            val member = boardMembers.find { it.id == memberId }

            if (member != null) {
                BoardMemberDetails(boardMember = member)
            }
            else {
                Text("Board member not found")
            }
        }

        composable<OrdersRoute> {
            PagedOrdersScreen(useTwoPaneLayout = useTwoPaneLayout)
        }

        composable<ToDosRoute> {
            ToDosScreen(useTwoPaneLayout = useTwoPaneLayout)
        }

        composable<SettingsRoute> {
            SettingsScreen()
        }

//        composable<LegacyArtistListRoute> {
//            LegacyArtistList(
//                artists = artists,
//                onArtistSelected = { artistId ->
//                    navController.navigate(ArtistDetailsRoute(artistId))
//                }
//            )
//        }
    }
}