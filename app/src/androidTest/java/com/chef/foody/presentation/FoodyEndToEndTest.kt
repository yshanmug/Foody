package com.chef.foody.presentation

import HomeScreen
import ProfileScreen
import android.content.Context
import android.os.SystemClock
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertValueEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.chef.foody.MainActivity
import com.chef.foody.di.AppModule
import com.chef.foody.di.RepositoryModule
import com.chef.foody.screens.CartScreen
import com.chef.foody.screens.LoginScreen
import com.chef.foody.screens.OrderFoodScreen
import com.chef.foody.screens.SignUpScreen
import com.chef.foody.screens.SplashScreen
import com.chef.foody.ui.theme.FoodyTheme
import com.chef.foody.util.Screen
import com.chef.foody.util.TEST_TAG
import com.chef.foody.util.TEST_TAG.NOTE_TO_CHEF_TEXT_FIELD
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import kotlin.random.Random


@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@UninstallModules(AppModule::class, RepositoryModule::class)
class FoodyEndToEndTest{

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            FoodyTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.SplashScreen.route
                ) {

                    composable(route = Screen.SplashScreen.route)
                    {
                        SplashScreen(isTesting = true,
                            onNavigateToLogin = {
                                Log.d("LOGIN_STATUS_", "LOGIN SCREEN")
                                navController.navigate(Screen.LoginScreen.route)
                            },
                            onNavigateToHome = {
                                Log.d("LOGIN_STATUS_", "SUCCESSFUL")
                                navController.navigate(Screen.CookHomeScreen.route)
                            })
                    }
                    composable(route = Screen.LoginScreen.route) {
                        LoginScreen(isTesting = true,
                            onNavigateToSignUp = {
                                navController.navigate(Screen.SignupScreen.route)
                            },
                            onNavigateToHome = {
                                navController.navigate(Screen.CookHomeScreen.route)
                            }
                        )
                    }
                    composable(route = Screen.SignupScreen.route) {
                        SignUpScreen(
                            onNavigateToHome = {
                                navController.navigate(Screen.CookHomeScreen.route)
                            }
                        )
                    }
                    composable(route = Screen.CookHomeScreen.route) {
                        Log.d("LOGIN_STATUS_", "CookHomeScreen HOME")
                        HomeScreen(isTesting = true,onProfileIconClicked = {
                            navController.navigate(Screen.ProfileScreen.route)
                        })
                    }
                    composable(route = Screen.CartScreen.route) {
                        CartScreen(modifier = Modifier)
                    }
                    composable(route = Screen.OrderFoodScreen.route) {
                        OrderFoodScreen(onNavigateCart = { navController.navigate(Screen.CartScreen.route) }
                        )
                    }

                    composable(route = Screen.ProfileScreen.route) {
                        Log.d("LOGIN_STATUS_", "ProfileScreen.route")
                        ProfileScreen(onBackButtonClicked = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }


    @After
    fun teardown()
    {
        val context = InstrumentationRegistry.getInstrumentation().context
        val datastoreDir = File(context.filesDir, "datastore")
        try {
            if (datastoreDir.exists()) {
                datastoreDir.deleteRecursively()
            }
        } catch (e: Exception) {
            println("Failed to delete datastore directory: ${e.message}")
        }
    }

    @Test
    fun testFoodOrderFlow()
    {
        composeRule.onNodeWithText("Forget Password").assertIsDisplayed()
        val emailTextField = composeRule.onNodeWithText("Enter Your Email")
        emailTextField.assertIsDisplayed()
        emailTextField.performClick()
        emailTextField.performTextInput("auroraborails@gmail.com")

        val passwordTextField = composeRule.onNodeWithText("Enter Your Password")
        passwordTextField.assertIsDisplayed()
        passwordTextField.performClick()
        passwordTextField.performTextInput("testPassword@123")

        val loginButton= composeRule.onNodeWithTag(TEST_TAG.LOGIN_BUTTON_CLICK)
        loginButton.performClick()
        SystemClock.sleep(1000)
        composeRule.onNodeWithText("Cook virtually, dine deliciously!").assertIsDisplayed()
        composeRule.onNodeWithText("Order Food").assertIsDisplayed()
        composeRule.onNodeWithTag(TEST_TAG.ORDER_FOOD_BUTTON_CLICK).performClick()
        composeRule.onNodeWithText("Find Your Dish").assertIsDisplayed()
        composeRule.onNodeWithText("Hamburger").assertIsDisplayed()
        composeRule.onNodeWithTag(TEST_TAG.FOOD_ORDER_LIST_LAZY_COLUMN).performScrollToIndex(3)
        composeRule.onAllNodesWithTag(TEST_TAG.QUANTITY_INCREASE_BUTTON_CLICK)[0].performClick()
        composeRule.onAllNodesWithTag(TEST_TAG.ADD_TO_CART_BUTTON_CLICK)[0].performClick()
        composeRule.onAllNodesWithTag(TEST_TAG.QUANTITY_INCREASE_BUTTON_CLICK)[1].performClick()
        composeRule.onAllNodesWithTag(TEST_TAG.QUANTITY_INCREASE_BUTTON_CLICK)[1].performClick()
        composeRule.onAllNodesWithTag(TEST_TAG.QUANTITY_INCREASE_BUTTON_CLICK)[1].performClick()
        composeRule.onAllNodesWithTag(TEST_TAG.ADD_TO_CART_BUTTON_CLICK)[1].performClick()
        composeRule.onAllNodesWithTag(TEST_TAG.QUANTITY_INCREASE_BUTTON_CLICK)[2].performClick()
        composeRule.onAllNodesWithTag(TEST_TAG.ADD_TO_CART_BUTTON_CLICK)[2].performClick()
        composeRule.onNodeWithTag(TEST_TAG.VIEW_CART_BUTTON_CLICKED).performClick()
        composeRule.onNodeWithText("Delivery Options").assertIsDisplayed()
        composeRule.onNodeWithText("Proceed to Find Offers").assertIsDisplayed()
        composeRule.onNodeWithTag(TEST_TAG.PROCEED_TO_FIND_ORDERS_BUTTON_CLICKED).assertIsDisplayed()
        composeRule.onNodeWithTag(TEST_TAG.PROCEED_TO_FIND_ORDERS_BUTTON_CLICKED).performClick()
    }


    @Test
    fun testRentChefFlow()
    {
        composeRule.onNodeWithText("Forget Password").assertIsDisplayed()
        val emailTextField = composeRule.onNodeWithText("Enter Your Email")
        emailTextField.performClick()
        emailTextField.performTextInput("auroraborails@gmail.com")

        val passwordTextField = composeRule.onNodeWithText("Enter Your Password")
        passwordTextField.performClick()
        passwordTextField.performTextInput("testPassword@123")

        val loginButton= composeRule.onNodeWithTag(TEST_TAG.LOGIN_BUTTON_CLICK)
        loginButton.performClick()
        SystemClock.sleep(1000)
        composeRule.onNodeWithText("Cook virtually, dine deliciously!").assertIsDisplayed()
        composeRule.onNodeWithText("Order Food").assertIsDisplayed()
        composeRule.onNodeWithTag(TEST_TAG.PERSONAL_CHEF_BUTTON_CLICK).performClick()
        val noteTextField = composeRule.onNodeWithTag(NOTE_TO_CHEF_TEXT_FIELD)
        noteTextField.assertIsDisplayed()
        noteTextField.performClick()
        noteTextField.performTextInput("I have egg, corn and mushroom")
        composeRule.onNodeWithTag(TEST_TAG.FIND_A_CHEF_WITH_NOTE_BUTTON_CLICK).performClick()

    }

    @Test
    fun testChefQuotationSendFlow()
    {
        val randomNumber = Random.nextInt(0,500)
        composeRule.onNodeWithText("Forget Password").assertIsDisplayed()
        composeRule.onNodeWithTag(TEST_TAG.LOGIN_SCREEN_REGISTER_BUTTON_CLICK).performClick()
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
        composeRule.onNodeWithText("Cook virtually, dine deliciously!").assertIsDisplayed()
        composeRule.onNodeWithText("Order Food").assertIsDisplayed()
        composeRule.onNodeWithTag(TEST_TAG.CHEF_BUTTON_CLICK).performClick()
        composeRule.onNodeWithText("Food Safety Certificate ").assertIsDisplayed()
        composeRule.onNodeWithText("Privacy Notice ").assertIsDisplayed()
        composeRule.onNodeWithText("File Format Information ").assertIsDisplayed()
        val chefCertificateUploadButton = composeRule.onNodeWithTag(TEST_TAG.CHEF_FILE_UPLOAD_BUTTON)
        chefCertificateUploadButton.assertIsDisplayed()
        chefCertificateUploadButton.performClick()
    }

    @Test
    fun testProfileScreen()
    {
        composeRule.onNodeWithText("Forget Password").assertIsDisplayed()
        val emailTextField = composeRule.onNodeWithText("Enter Your Email")
        emailTextField.assertIsDisplayed()
        emailTextField.performClick()
        emailTextField.performTextInput("auroraborails@gmail.com")

        val passwordTextField = composeRule.onNodeWithText("Enter Your Password")
        passwordTextField.assertIsDisplayed()
        passwordTextField.performClick()
        passwordTextField.performTextInput("testPassword@123")

        val loginButton= composeRule.onNodeWithTag(TEST_TAG.LOGIN_BUTTON_CLICK)
        loginButton.performClick()
        SystemClock.sleep(1000)
        composeRule.onNodeWithText("Cook virtually, dine deliciously!").assertIsDisplayed()
        composeRule.onNodeWithText("Order Food").assertIsDisplayed()

        composeRule.onNodeWithTag(TEST_TAG.PROFILE_SCREEN_ICON_CLICKED).performClick()
        composeRule.onNodeWithText("Profile").assertIsDisplayed()

        composeRule.onNodeWithTag(TEST_TAG.PROFILE_EMAIL_TEXT).assertTextEquals("auroraborails@gmail.com")
        val oldAddressTextField = composeRule.onNodeWithText("299 Albert Street, Waterloo, Ontario, postal code: N2L3T8")
        oldAddressTextField.assertIsDisplayed()
        oldAddressTextField.performTextClearance()
        val addressTextField = composeRule.onNodeWithText("Enter Your Address")
        addressTextField.assertIsDisplayed()
        addressTextField.performTextInput("299 Albert Street, Waterloo, Ontario, postal code: N2L3T8")
        composeRule.onNodeWithTag(TEST_TAG.PROFILE_ADDRESS_UPDATE_BUTTON).performClick()
        SystemClock.sleep(1000)
        composeRule.onNodeWithText("299 Albert Street, Waterloo, Ontario, postal code: N2L3T8").assertIsDisplayed()
    }

}
