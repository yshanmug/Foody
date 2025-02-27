package com.chef.foody.screens


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chef.foody.R
import com.chef.foody.domain.model.LoginStatus
import com.chef.foody.events.UserSignUpEvent
import com.chef.foody.util.Colors
import com.chef.foody.util.CredentialValidation
import com.chef.foody.util.TEST_TAG
import com.chef.foody.viewmodels.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(onNavigateToHome:()->Unit, userViewModel: UserViewModel = hiltViewModel()) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val ctx = LocalContext.current
    LaunchedEffect(key1 = userViewModel.userState.value.isUserLoggedIn) {
        when(userViewModel.userState.value.isUserLoggedIn.name)
        {
            LoginStatus.LoginFailed.name -> Toast.makeText(ctx,"Registration Failed", Toast.LENGTH_SHORT).show()
            LoginStatus.LoginSuccessful.name -> onNavigateToHome()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp, vertical = 70.dp),
//                horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable { }
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(50.dp, vertical = 0.dp),
            ) {
                Text(
                modifier = Modifier
                    .padding(40.dp, 0.dp, 0.dp, 0.dp),
                    text = "Sign Up",
                    fontSize = 20.sp,
                    color = Colors.primaryOrange,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Column(modifier = Modifier.padding(0.dp,0.dp,0.dp)){
            Text(
                modifier = Modifier
                    .padding(15.dp, 0.dp, 0.dp, 5.dp),
                text = "First Name",
                color = Color.Black,
//                    fontSize = 15.sp,
            )
            TextField(
                value = firstName,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .shadow(5.dp, RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Colors.white,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                label ={
                    Text("Enter Your First Name", color = Colors.grey)
                },
                onValueChange = {
                    firstName = it.trim()
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
//                    placeholder = {Text("Enter Your First Name", color =Colors.grey)}
            )
        }
        Column(modifier = Modifier.padding(0.dp,10.dp,0.dp)){
            Text(
                modifier = Modifier
                    .padding(15.dp, 0.dp, 0.dp, 5.dp),
                text = "Last Name",
                color = Color.Black,
//                    fontSize = 15.sp,
            )
            TextField(
                value = lastName,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .shadow(5.dp, RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Colors.white,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                label ={
                    Text("Enter Your Last Name", color = Colors.grey)
                },
                onValueChange = {
                    lastName = it.trim()
                }
//                    placeholder = {Text("Enter Your First Name", color =Colors.grey)}
            )
        }
        Column(modifier = Modifier.padding(0.dp,10.dp,0.dp)){
            Text(
                modifier = Modifier
                    .padding(15.dp, 0.dp, 0.dp, 5.dp),
                text = "Email",
                color = Color.Black,
//                    fontSize = 15.sp,
            )
            TextField(
                value = email,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .shadow(5.dp, RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Colors.white,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                label ={
                    Text("Enter Your Email", color = Colors.grey)
                },
//                    visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    email = it.trim()
                }
            )
        }
        Column(modifier = Modifier.padding(0.dp,10.dp,0.dp)){
            Text(
                modifier = Modifier
                    .padding(15.dp, 0.dp, 0.dp, 5.dp),
                text = "New Password",
                color = Color.Black,
            )
            TextField(
                value = newPassword,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .shadow(5.dp, RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Colors.white,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                label ={
                    Text("Enter Your New Password", color = Colors.grey)
                },
                onValueChange = {
                    newPassword = it.trim()
                }
//                    placeholder = {Text("Enter Your First Name", color =Colors.grey)}
            )
        }
        Column(modifier = Modifier.padding(0.dp,10.dp,0.dp)){
            Text(
                modifier = Modifier
                    .padding(15.dp, 0.dp, 0.dp, 5.dp),
                text = "Confirm Password",
                color = Color.Black,
            )
            TextField(
                value = confirmPassword,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .shadow(5.dp, RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Colors.white,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                label ={
                    Text("Enter Your Confirm Password", color = Colors.grey)
                },
                onValueChange = {
                    confirmPassword = it.trim()
                }

            )
        }
        Column(modifier = Modifier.padding(0.dp,40.dp,0.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {
                    //email format
                    if (!CredentialValidation.emailValid(email)){
                        Toast.makeText(ctx, "Invalid Email Address", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    // password format
                    if (!CredentialValidation.newPasswordValid(newPassword)) {
                        Toast.makeText(ctx,
                            "password contains only alphabets, numbers and " +
                                    "'@#_', with length of 8-16",
                            Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (!CredentialValidation.confirmPasswordValid(confirmPassword)) {
                        Toast.makeText(ctx,
                            "password contains only alphabets, numbers and " +
                                    "'@#_', with length of 8-16",
                            Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                        userViewModel.onSignupEvent(UserSignUpEvent.Register(firstName,lastName,email,newPassword,confirmPassword))
                },
                colors = ButtonDefaults.buttonColors(containerColor = Colors.primaryOrange),
                modifier = Modifier
                    .size(width = 290.dp, height = 50.dp ).testTag(TEST_TAG.REGISTER_BUTTON_CLICK)
                ) {
                Text(text = "Register", color = Color.White)
            }

        }

    }

}




//
//@Preview(showBackground = true)
//@Composable
//fun SignUpScreenPreview(){
//    SignUpScreen()
//}
