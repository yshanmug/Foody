package com.chef.foody.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chef.foody.R
import com.chef.foody.domain.model.ChefStatus
import com.chef.foody.viewmodels.UserViewModel



@Composable
fun ChefHomeRouteScreen(
    modifier: Modifier,
    onNavigationUnverified: ()->Unit = {},
    onNavigationVerified: ()->Unit = {},
    userViewModel: UserViewModel = hiltViewModel()) {
    val userState by remember {userViewModel.userState}
    when (userState.chefStatus) {
        null -> {
            Log.d("ChefHomeRoutingScreen", "chefStatus is null, retrieving it from server")
            LaunchedEffect(Unit) {
                userViewModel.onCheckChefStatus()
            }
        }
        ChefStatus.VERIFIED -> {
            LaunchedEffect(Unit) {onNavigationVerified()}
        }
        else -> {
//        ChefStatus.NOT_VERIFIED ChefStatus.VERIFICATION_IN_PROGRESS
            LaunchedEffect(Unit) {onNavigationUnverified()}
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        LoopAnimation(modifier)
        Text(text = "Retrieving Chef Status...", fontSize = 16.sp, fontStyle = FontStyle.Italic)
    }
}

