

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chef.foody.R
import com.chef.foody.domain.model.ChefStatus
import com.chef.foody.screens.BottomBar
import com.chef.foody.screens.CartScreen
import com.chef.foody.screens.ChefCertificateUploadScreen
import com.chef.foody.screens.ChefConfirmedScreen
import com.chef.foody.screens.ChefHomeRouteScreen
import com.chef.foody.screens.ChefHomeScreen
import com.chef.foody.screens.ChefOrderStatusDoorScreen
import com.chef.foody.screens.ChefOrderStatusPickupScreen
import com.chef.foody.screens.FindChefScreen
import com.chef.foody.screens.FindOffersScreen
import com.chef.foody.screens.OrderFoodScreen
import com.chef.foody.screens.RentChefScreen
import com.chef.foody.screens.RentalChefStatusScreen
import com.chef.foody.screens.TopBarGreeting
import com.chef.foody.screens.UserOrderDeliveryStatusScreen
import com.chef.foody.screens.UserOrderPickupStatusScreen
import com.chef.foody.util.AppConstants.CURRENT_ACTIVE_ORDER_ID
import com.chef.foody.util.Screen
import com.chef.foody.util.TEST_TAG
import com.chef.foody.viewmodels.UserViewModel


@Composable
fun HomeScreen(isTesting: Boolean = false,userViewModel: UserViewModel = hiltViewModel(),onProfileIconClicked:() -> Unit = {}) {//viewModel: UserViewModel =  hiltViewModel()) {

    val navController = rememberNavController()
    val userType = remember {
        mutableStateOf(UserType.COOK)
    }
    Scaffold(
        bottomBar = {
           BottomBar(onCookScreenNavigation = {
               navController.navigate(UserType.COOK.name)
           }, onChefScreenNavigation = {
               navController.navigate(UserType.CHEF.name)
           })
        }, topBar = {
            TopBarGreeting(onProfileIconClicked = onProfileIconClicked)
        }, content = {
            MainScreenNavigationConfigurations(
                Modifier.padding(it),
                navController = navController,
                isTesting = isTesting,
                userViewModel = userViewModel
            )
        }
    )
    }


@Composable
private fun MainScreenNavigationConfigurations(
    modifier: Modifier,
    navController: NavHostController,
    isTesting: Boolean,
    userViewModel: UserViewModel = hiltViewModel()
) {
    NavHost(navController, startDestination = UserType.COOK.name) {
        composable(UserType.COOK.name) {
            CookHomeScreen(modifier = modifier, isTesting = isTesting, onNavigate = {
                navController.navigate(it)
            })
        }
        composable(Screen.OrderFoodScreen.route)
        {

            OrderFoodScreen(
                modifier = modifier,
                onNavigateCart = {navController.navigate(Screen.CartScreen.route)},
                userViewModel = userViewModel
            )
        }
        composable(Screen.FindRentalChef.route) {
            FindChefScreen(
                modifier = modifier,
                onNavigateToChefConfirmed = {navController.navigate(Screen.ChefConfirmedScreen.route)},
                )
        }



        composable(route = Screen.ChefConfirmedScreen.route) {
            ChefConfirmedScreen(modifier)
        }

        composable(Screen.RentAChefScreen.route)
        {
            RentChefScreen(
//                modifier = modifier,
                onNavigateToQuotationScreen = {
                    navController.navigate(Screen.FindRentalChef.route)
                                              },
//                userViewModel = userViewModel
            )
        }





        composable(UserType.CHEF.name) {
            LaunchedEffect(Unit) {
                userViewModel.onCheckChefStatus()
            }
            // if verified then navigate to chef home screen
            ChefHomeRouteScreen(
                modifier = modifier,
                onNavigationVerified = { navController.navigate(Screen.ChefHomeScreen.route) },
                onNavigationUnverified = { navController.navigate(Screen.ChefCertificateUploadScreen.route) }
            )
//            if(userViewModel.userState.value.chefStatus == ChefStatus.VERIFIED)
//            {
//                ChefHomeScreen(modifier = modifier,
//                    onNavigationChefOrderConfirmedDoor = {navController.navigate(Screen.ChefOrderConfirmedDoor.route)},
//                    onNavigationChefOrderConfirmedPickup = {navController.navigate(Screen.ChefOrderConfirmedPickup.route)}
//                )
////                ChefHomeRouteScreen(
////                    modifier = modifier,
////                    onNavigationVerified = { navController.navigate(Screen.ChefHomeScreen.route) },
////                    onNavigationUnverified = { navController.navigate(Screen.ChefCertificateUploadScreen.route) }
////                )
//            }
//            else
//            {
//                navController.navigate(Screen.ChefCertificateUploadScreen.route)
//            }
//            //ChefCertificateUploadScreen(modifier = modifier)

        }
        composable(Screen.ChefOrderConfirmedPickup.route) {
            ChefOrderStatusPickupScreen(modifier = modifier)
        }
        composable(Screen.ChefOrderConfirmedDoor.route) {
            ChefOrderStatusDoorScreen(modifier = modifier)
        }
        composable("${Screen.RentalChefStatusScreen.route}/{${CURRENT_ACTIVE_ORDER_ID}}") {
            backStackEntry-> val orderID = backStackEntry.arguments?.getString(CURRENT_ACTIVE_ORDER_ID)
            orderID?.let {
                RentalChefStatusScreen(modifier = modifier,currentOrderID = orderID.toInt())
            }


        }
        composable(Screen.CartScreen.route){
            CartScreen(
                modifier = modifier,
                onNavigateFindOffers = {navController.navigate(Screen.FindOffersScreen.route)},
                userViewModel = userViewModel
            )
        }
//        composable(Screen.ChefHomeRouteScreen.route) {
//            ChefHomeRouteScreen(
//                modifier = modifier,
//                onNavigationUnverified = {navController.navigate(Screen.ChefCertificateUploadScreen.route)},
//                onNavigationVerified = {navController.navigate(Screen.ChefHomeScreen.route)}
//            )
//        }
        composable(Screen.ChefHomeScreen.route) {
            ChefHomeScreen(
                modifier = modifier,
                onNavigationChefOrderConfirmedPickup = {navController.navigate(Screen.ChefOrderConfirmedPickup.route)},
                onNavigationChefOrderConfirmedDoor = {navController.navigate(Screen.ChefOrderConfirmedDoor.route)},
                onNavigationChefRentChefConfirmed = {navController.navigate("${Screen.RentalChefStatusScreen.route}/${it}")}
            )
        }
        composable(Screen.ChefCertificateUploadScreen.route) {
            ChefCertificateUploadScreen(modifier = modifier, onNavigationChefToChefHomeScreenAfterVerification =
            {
                navController.navigate(UserType.CHEF.name)
            }
            )
        }
        composable(Screen.ChefCertificateVerificationInProgressScreen.route) {
            // ChefCertificateVerificationInProgressScreen(modifier = modifier)
        }

        composable(route = Screen.UserOrderPickupStatusScreen.route) {
            UserOrderPickupStatusScreen(modifier)
        }
        composable(route = Screen.UserOrderDeliveryStatusScreen.route) {
            UserOrderDeliveryStatusScreen(modifier)
        }

        composable(Screen.FindOffersScreen.route){
            userViewModel.resetQuotationList()
            FindOffersScreen(modifier = modifier, userViewModel = userViewModel,
                onNavigateToDeliveryStatus={

                    navController.navigate(Screen.UserOrderDeliveryStatusScreen.route)
                },
            onNavigateToPickupStatus={
                navController.navigate(Screen.UserOrderPickupStatusScreen.route)
            }
            )
        }
    }
}
enum class UserType{
    COOK,
    CHEF
}


@Composable
 fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString("KEY_ROUTE")
}


@Composable
fun CookHomeScreen(onNavigate:(String) -> Unit,isTesting: Boolean, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Box - Order Food Button
        if(isTesting)
        {
            Box(
                modifier = Modifier.testTag(TEST_TAG.ORDER_FOOD_BUTTON_CLICK)
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = 20.dp)
                    .shadow(10.dp, RoundedCornerShape(25.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = Color(0xFFFFE6C7))
                    .clickable {
                        onNavigate(Screen.OrderFoodScreen.route)
                    }, contentAlignment = Alignment.Center) {
                Text(text = "Order Food")
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = 20.dp)
                    .shadow(10.dp, RoundedCornerShape(25.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = Color(0xFFFFE6C7))
                    .clickable {
                    }, contentAlignment = Alignment.Center) {
                Text(text = "Cook Virtually")
            }
            Box(
                modifier = Modifier.testTag(TEST_TAG.PERSONAL_CHEF_BUTTON_CLICK)
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = 20.dp)
                    .shadow(10.dp, RoundedCornerShape(25.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = Color(0xFFFFE6C7))
                    .clickable {
                        onNavigate(Screen.RentAChefScreen.route)
                    }, contentAlignment = Alignment.Center) {
                Text(text = "Personal Chef")
            }
        }
        else
        {

            val preloader1LottieComposition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(
                    R.raw.order_food_animation
                )
            )

            val preloader2LottieComposition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(
                    R.raw.cook_virtually_animation
                )
            )

            val preloader3LottieComposition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(
                    R.raw.rent_chef_animation
                )
            )

            val preloader1Progress by animateLottieCompositionAsState(
                preloader1LottieComposition,
                iterations = LottieConstants.IterateForever,
                isPlaying = true
            )

            val preloader2Progress by animateLottieCompositionAsState(
                preloader2LottieComposition,
                iterations = LottieConstants.IterateForever,
                isPlaying = true
            )

            val preloader3Progress by animateLottieCompositionAsState(
                preloader3LottieComposition,
                iterations = LottieConstants.IterateForever,
                isPlaying = true
            )
            ButtonBox(preloader1LottieComposition, preloader1Progress, "Order Food",onNavigate = {
                onNavigate(Screen.OrderFoodScreen.route)
            })

            // box - Cook Virtually Button
            ButtonBox(preloader2LottieComposition, preloader2Progress, "Cook Virtually",onNavigate={

            })

            // box - Rent a Personal Chef Button
            ButtonBox(preloader3LottieComposition, preloader3Progress, "Personal Chef",onNavigate={
                onNavigate(Screen.RentAChefScreen.route)
            })

        }



    }
}

@Composable
fun ButtonBox(
    composition: LottieComposition?,
    progress: Float,
    buttonText: String,
    onNavigate:() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 20.dp)
            .shadow(10.dp, RoundedCornerShape(25.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(color = Color(0xFFFFE6C7))
            .clickable {
                onNavigate()
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.size(125.dp)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color(0xFFFAA73D))
                    .fillMaxHeight()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buttonText,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}