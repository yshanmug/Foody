package com.chef.foody

import HomeScreen
import ProfileScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chef.foody.screens.CartScreen
import com.chef.foody.screens.FindOffersScreen
import com.chef.foody.screens.LoginScreen
import com.chef.foody.screens.OrderFoodScreen
import com.chef.foody.screens.SignUpScreen
import com.chef.foody.screens.SplashScreen
import com.chef.foody.screens.UserOrderDeliveryStatusScreen
import com.chef.foody.screens.UserOrderPickupStatusScreen
import com.chef.foody.ui.theme.FoodyTheme
import com.chef.foody.util.Screen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        installSplashScreen()
        setContent {
            FoodyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SplashScreen.route
                    ) {

                        composable(route = Screen.SplashScreen.route)
                        {
                            SplashScreen(
                                onNavigateToLogin = {
                                    navController.navigate(Screen.LoginScreen.route)
                                                    },
                                onNavigateToHome = {
                                    navController.navigate(Screen.CookHomeScreen.route)
                                })
                        }
                        composable(route = Screen.LoginScreen.route) {
                            LoginScreen(
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
                            HomeScreen(onProfileIconClicked = {
                                navController.navigate(Screen.ProfileScreen.route)
                            })
                        }
                        composable(route = Screen.CartScreen.route) {
                            CartScreen(modifier = Modifier)
                        }
                        composable(route = Screen.OrderFoodScreen.route) {
                            OrderFoodScreen(onNavigateCart = {navController.navigate(Screen.CartScreen.route)}
                            )
                        }

                        composable(route = Screen.ProfileScreen.route) {
                            ProfileScreen(onBackButtonClicked = {
                                navController.popBackStack()
                            })
                        }
//                        composable(route = Screen.FindOffersScreen.route){
//                            FindOffersScreen()
////                            FindOffersScreen(onNavigateToUserDelivery = {navController.navigate(Screen.UserOrderDeliveryStatusScreen.route)}
////                            )
//                        }

                    }

                }
            }
        }
    }
}

