package com.chef.foody.screens
import android.util.Log
import com.chef.foody.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.chef.foody.domain.model.LoginStatus
import com.chef.foody.util.Colors
import com.chef.foody.util.Screen
import com.chef.foody.viewmodels.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


@Composable
fun SplashScreen(isTesting: Boolean = false,onNavigateToLogin:() -> Unit, onNavigateToHome:()->Unit, userViewModel: UserViewModel = hiltViewModel()) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black), contentAlignment = Alignment.Center)
    {
        Image(painter = painterResource(id = R.drawable.splash_screen_icon), contentDescription = "LOGO")
    }
    LaunchedEffect(key1  = "splash")
    {
        if(!isTesting)
        {
            delay(200)
        }

        when(userViewModel.userState.value.isUserLoggedIn){
            LoginStatus.LoginSuccessful -> {
                Log.d("LOGIN_STATUS_","LoginStatus.LoginSuccessful")
                onNavigateToHome.invoke()
            }
            LoginStatus.LoginFailed ->
            {
                Log.d("LOGIN_STATUS_","LoginStatus.LoginFailed")
                onNavigateToLogin.invoke()
            }
            LoginStatus.Logout -> {
                Log.d("LOGIN_STATUS_"," LoginStatus.Logout")
                onNavigateToLogin.invoke()
            }
        }
    }

}
