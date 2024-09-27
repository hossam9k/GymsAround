package com.example.gymsaround

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.gymsaround.DummyGymsList.getDomainDummyGymsList
import com.example.gymsaround.gym.presentaion.SemanticsDescription
import com.example.gymsaround.gym.presentaion.gymslist.GymsScreen
import com.example.gymsaround.gym.presentaion.gymslist.GymsScreenState
import com.example.gymsaround.ui.theme.GymsAroundTheme
import org.junit.Rule
import org.junit.Test

class GymsScreenTest {

    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun loadingState_isActive() {
        testRule.setContent {
            GymsAroundTheme {
                GymsScreen(
                    state = GymsScreenState(
                        gyms = emptyList(),
                        isLoading = true,
                    ),
                    onItemClick = {},
                    onFavouriteItemClick = { _: Int, _: Boolean -> }
                )
            }
        }
        testRule.onNodeWithContentDescription(SemanticsDescription.GYM_LIST_LOADING)
            .assertIsDisplayed()
    }

    @Test
    fun loadedContentState_isActive() {
        val gymsList = getDomainDummyGymsList()

        testRule.setContent {
            GymsAroundTheme {
                GymsScreen(state = GymsScreenState(
                    gyms = gymsList,
                    isLoading = false,
                ), onItemClick = {}, onFavouriteItemClick = { _: Int, _: Boolean -> }
                )
            }
        }
        testRule.onNodeWithText(gymsList[0].name).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticsDescription.GYM_LIST_LOADING)
            .assertDoesNotExist()
    }

    @Test
    fun errorState_isActive() {
        val errorTest = "Field to load data"

        testRule.setContent {
            GymsAroundTheme {
                GymsScreen(
                    GymsScreenState(
                        gyms = emptyList(),
                        isLoading = false,
                        error = errorTest,
                    ), onItemClick = {}, onFavouriteItemClick = { _: Int, _: Boolean ->
                    }
                )
            }
        }
        testRule.onNodeWithText(errorTest).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticsDescription.GYM_LIST_LOADING)
            .assertDoesNotExist()
    }

    @Test
    fun isItemClick_idIsPassedCorrectly() {
        val gymsList = getDomainDummyGymsList()
        val gymItem = gymsList[0]
        testRule.setContent {
            GymsAroundTheme {
                GymsScreen(state = GymsScreenState(
                    gyms = gymsList,
                    isLoading = false,
                ), onItemClick = { id ->
                    assert(id == gymItem.id)
                }, onFavouriteItemClick = { _: Int, _: Boolean -> }
                )
            }
        }
        testRule.onNodeWithText(gymItem.name).performClick()
    }
}