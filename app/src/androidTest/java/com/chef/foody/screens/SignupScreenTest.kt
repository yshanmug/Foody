package com.chef.foody.screens

import android.os.SystemClock
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.chef.foody.MainActivity
import com.chef.foody.di.AppModule
import com.chef.foody.di.RepositoryModule
import com.chef.foody.util.TEST_TAG
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random


@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@UninstallModules(AppModule::class, RepositoryModule::class)
class SignupScreenTest{
    
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
           SignUpScreen(onNavigateToHome = {})
        }
    }
    @Test
    fun testRegisterFlow(){
        val randomNumber = Random.nextInt(0,500)
        val firstNameTextField = composeRule.onNodeWithText("Enter Your First Name")
        firstNameTextField.performClick()
        firstNameTextField.performTextInput("Test First Name $randomNumber")
        val lastNameTextField = composeRule.onNodeWithText("Enter Your Last Name")
        lastNameTextField.performClick()
        lastNameTextField.performTextInput("Test Last Name $randomNumber")
        val mailTextField = composeRule.onNodeWithText("Enter Your Email")
        mailTextField.performClick()
        mailTextField.performTextInput("testemail$randomNumber@gmail.com")
        val passwordTextField = composeRule.onNodeWithText("Enter Your New Password")
        passwordTextField.performClick()
        passwordTextField.performTextInput("pass@123")
        val confirmPasswordTextField = composeRule.onNodeWithText("Enter Your Confirm Password")
        confirmPasswordTextField.performClick()
        confirmPasswordTextField.performTextInput("pass@123")
        val registerButton= composeRule.onNodeWithTag(TEST_TAG.REGISTER_BUTTON_CLICK)
        registerButton.performClick()
        SystemClock.sleep(2000)
    }

}