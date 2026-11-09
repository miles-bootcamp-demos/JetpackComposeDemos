package com.pnc.miles.jetpackcomposedemos.features.orders.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import com.pnc.miles.jetpackcomposedemos.HiltTestActivity
import com.pnc.miles.jetpackcomposedemos.features.authentication.domain.SessionManager
import com.pnc.miles.jetpackcomposedemos.features.authentication.domain.User
import com.pnc.miles.jetpackcomposedemos.testutil.builders.aMyOrder
import com.pnc.miles.jetpackcomposedemos.testutil.builders.aMyOrderLine
import com.pnc.miles.jetpackcomposedemos.testutil.builders.aMyOrdersResponse
import com.pnc.miles.jetpackcomposedemos.testutil.fakes.FakeMyOrdersDataSource
import com.pnc.miles.jetpackcomposedemos.ui.theme.KazooTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltAndroidTest
class PagedOrdersScreenIntegrationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var session: SessionManager

    @Inject
    lateinit var ordersApi: FakeMyOrdersDataSource

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    private fun showScreen() {
        composeRule.setContent {
            KazooTheme {
                PagedOrdersScreen(useTwoPaneLayout = false)
            }
        }
    }

    private fun signInAs(id: String = "11000", name: String = "Cara Cole") {
        session.signIn(User(id, username = name, token = "test-token"))
    }

    // Paging loads data on a background thread, which Compose cannot see
    private fun waitForText(text: String) {
        composeRule.waitUntil(timeoutMillis = 15_000L) {
            composeRule.onAllNodesWithText(text, useUnmergedTree = true)
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    private fun httpError(code: Int) = HttpException(
        Response.error<Any>(code, "".toResponseBody("application/json".toMediaType()))
    )

    @Test
    fun givenSignedInCustomerWithOrders_whenScreenOpens_thenTheOrdersAreListed() {
        signInAs()
        ordersApi.response = aMyOrdersResponse(
            11000,
            aMyOrder(orderNumber = 1001, lines = listOf(aMyOrderLine(productName = "Road Bike"))),
            aMyOrder(orderNumber = 1002, lines = listOf(aMyOrderLine(lineId = 2, productName = "Helmet")))
        )

        showScreen()
        waitForText("Order #:1001").assertIsDisplayed()
        waitForText("Road Bike").assertIsDisplayed()
        waitForText("Helmet").assertIsDisplayed()
    }

    @Test
    fun givenAnExpiredSession_whenScreenOpens_thenAnExplanationAndRetryAreShown() {
        signInAs()
        ordersApi.failure = httpError(401)

        showScreen()
        waitForText("Error: Your session has expired. Please sign in again.")
        composeRule.onNodeWithText("Retry").assertIsDisplayed()
    }
}