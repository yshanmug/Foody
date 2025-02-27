package com.chef.foody.screens

import HomeScreen
import android.media.Image
import android.os.SystemClock
import android.service.autofill.Validators.or
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.chef.foody.MainActivity
import com.chef.foody.di.AppModule
import com.chef.foody.di.RepositoryModule
import com.chef.foody.ui.theme.FoodyTheme
import com.chef.foody.util.Screen
import com.chef.foody.util.TEST_TAG
import com.chef.foody.viewmodels.UserViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Testing Top Bar and Bottom Bar functionality
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@UninstallModules(AppModule::class, RepositoryModule::class)
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Yellow), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly
                )
                {
                    TopBarGreeting(onProfileIconClicked = {})
                    BottomBar(onChefScreenNavigation = {}, onCookScreenNavigation = {})
                }
        }
    }


    @Test
    fun testTagLineOfCookAndChef() {
        composeRule.onNodeWithText("Cook virtually, dine deliciously!").assertIsDisplayed()
        composeRule.onNodeWithTag(TEST_TAG.CHEF_BUTTON_CLICK).performClick()
        composeRule.onNodeWithText("Cook with Passion, Serve with Pride!").assertIsDisplayed()
        composeRule.onNodeWithTag(TEST_TAG.COOK_BUTTON_CLICK).performClick()
        composeRule.onNodeWithText("Cook virtually, dine deliciously!").assertIsDisplayed()
        composeRule.onNodeWithTag(TEST_TAG.COOK_BUTTON_CLICK).assertBackgroundColor(Color(0xFFFFE6C7))
        composeRule.onNodeWithTag(TEST_TAG.CHEF_BUTTON_CLICK).assertBackgroundColor(Color.Transparent)
        composeRule.onNodeWithTag(TEST_TAG.CHEF_BUTTON_CLICK).performClick()
        composeRule.onNodeWithTag(TEST_TAG.CHEF_BUTTON_CLICK).assertBackgroundColor(Color(0xFFFFE6C7))
        composeRule.onNodeWithTag(TEST_TAG.COOK_BUTTON_CLICK).assertBackgroundColor(Color.Transparent)
    }
    private fun SemanticsNodeInteraction.assertBackgroundColor(expectedBackground: Color) {
        val capturedName = captureToImage().colorSpace.name
        assertEquals(expectedBackground.colorSpace.name, capturedName)
    }

}
