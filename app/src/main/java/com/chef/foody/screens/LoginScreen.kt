package com.chef.foody.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.chef.foody.util.Colors
import com.chef.foody.R
import com.chef.foody.domain.model.LoginStatus
import com.chef.foody.events.UserLoginEvent
import com.chef.foody.util.CredentialValidation
import com.chef.foody.util.TEST_TAG
import com.chef.foody.viewmodels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(isTesting: Boolean = false,onNavigateToSignUp:() -> Unit, onNavigateToHome:()->Unit, userViewModel: UserViewModel = hiltViewModel()) {
    val w = .75f // width
    val p = 20.dp // padding top for each grouped components
    val s = 5.dp // shadow
    val recshape = RoundedCornerShape(10.dp) // textfield, login button
    val ctx = LocalContext.current
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = userViewModel.userState.value.isUserLoggedIn) {
        when(userViewModel.userState.value.isUserLoggedIn.name)
        {
            LoginStatus.LoginFailed.name -> Toast.makeText(ctx,"Login Failed",Toast.LENGTH_SHORT).show()
            LoginStatus.LoginSuccessful.name -> onNavigateToHome()
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
//            .border(1.dp, Color.Green)
        ) {

        if(isTesting)
        {
            Text(
                "Lottie Animation Place Holder",
                color = Colors.primaryOrange,
                fontSize = 20.sp,
                modifier = Modifier.size(200.dp)
            )
        }
        else
        {
            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.chef_animation)
            )
            val progress by animateLottieCompositionAsState(
                composition,
                iterations = LottieConstants.IterateForever,
                isPlaying = true
            )
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.size(200.dp)
            )
        }
        Text(
        "Login",
            color = Colors.primaryOrange,
            fontSize = 20.sp,
            modifier = Modifier.padding(0.dp,p,0.dp,0.dp)
        )
        Column(modifier = Modifier.padding(0.dp,p,0.dp,0.dp)) {
            Text(
                text = "Email",
                modifier = Modifier
//                        .border(1.dp, Color.Red)
                    .align(Alignment.Start)
                    .padding(15.dp, 0.dp, 0.dp, 5.dp)
            )
            TextField(
                value = email,
                shape = recshape,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Colors.white,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {Text("Enter Your Email",color=Colors.grey)},
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),

                /*
                isError = emailInvalid.value && emailComposable.value.isNotEmpty(),
                supportingText = {
                    if (emailInvalid.value && emailComposable.value.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "invalid email address",
                            color = Colors.primaryOrange
                        )
                    }
                },
                 */
                modifier = Modifier
//                        .border(1.dp, Color.Blue)
                    .fillMaxWidth(w)
                    .shadow(s, recshape),
                onValueChange = {
                    email = it.trim()
                }
            )
        }
        Column(modifier = Modifier.padding(0.dp,p,0.dp,0.dp)) {
            Text(
                text = "Password",
                modifier = Modifier
//                        .border(1.dp, Color.Red)
                    .align(Alignment.Start)
                    .padding(15.dp, 0.dp, 0.dp, 5.dp)
            )
            TextField(
                value = password,
                shape = recshape,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Colors.white,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {Text("Enter Your Password",color=Colors.grey)},
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                /*
                isError = passwordInvalid.value && passwordComposable.value.isNotEmpty(),
                supportingText = {
                    if (passwordInvalid.value && passwordComposable.value.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "password should be 8-16 characters with only alphabets and numbers",
                            color = Colors.primaryOrange
                        )
                    }
                },
                 */
                modifier = Modifier
//                        .border(1.dp, Color.Blue)
                    .fillMaxWidth(w)
                    .shadow(s, recshape),
                onValueChange = {
                    password = it.trim()
                }
            )
            TextButton(
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
//                        .height(IntrinsicSize.Min)
//                        .background(Color.Transparent)
                    .align(Alignment.End),
                onClick = {
                    Toast.makeText(ctx, "Not Implemented Yet", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text("Forget Password",color=Colors.primaryOrange)
            }
        }
//        BoxWithConstraints {
//            Box(modifier = Modifier
//                .width(maxWidth).height(maxHeight)
//                .background(Colors.primaryOrange))
//        }

        Button(
            shape = recshape,
            colors = ButtonDefaults.buttonColors(containerColor = Colors.primaryOrange),
            modifier = Modifier
                .padding(0.dp, p, 0.dp, 0.dp)
//            .border(1.dp, Color.Green)
                .fillMaxWidth(w).testTag(TEST_TAG.LOGIN_BUTTON_CLICK),
            onClick = {
                // email format
                if (!CredentialValidation.emailValid(email)) {
                    Toast.makeText(ctx, "Invalid Email Address", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                // password format
                if (!CredentialValidation.passwordValid(password)) {
                    Toast.makeText(ctx, "password contains only alphabets, numbers and " +
                                "'@#_', with length of 8-16",
                        Toast.LENGTH_SHORT).show()
                    return@Button
                }
                // TODO: login logic
                userViewModel.onLoginEvent(UserLoginEvent.Login(email,password))
                // Toast.makeText(ctx,"Email & Password Verified",Toast.LENGTH_SHORT).show()
        }) {
            Text("Login")
        }
        Row(
            modifier = Modifier
                .padding(0.dp, p, 0.dp, 0.dp)
//            .border(1.dp, Color.Gray)
                .fillMaxWidth(w)) {
            val dividerW = .45f // dividerW < .5f
            Divider(
                color = Colors.primaryOrange,
                modifier = Modifier
                    .fillMaxWidth(dividerW)
                    .align(Alignment.CenterVertically)
            )
            Text("OR",
                textAlign = TextAlign.Center,
                color = Colors.primaryOrange,
                modifier = Modifier.fillMaxWidth(1-dividerW/(1-dividerW))
            )
            Divider(
                color = Colors.primaryOrange,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(0.dp, p, 0.dp, 0.dp)
                .height(IntrinsicSize.Min)
        ) {
            Text(
                "Don't Have An Account? "
            )
            TextButton(
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.testTag(TEST_TAG.LOGIN_SCREEN_REGISTER_BUTTON_CLICK),
                onClick = { onNavigateToSignUp() }
            ) {
                Text("Register",color=Colors.primaryOrange)
            }
        }
    }
}
