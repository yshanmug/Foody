
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chef.foody.R
import com.chef.foody.events.AddressUpdateEvent
import com.chef.foody.util.Colors
import com.chef.foody.util.TEST_TAG
import com.chef.foody.viewmodels.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(userViewModel: UserViewModel = hiltViewModel(), onBackButtonClicked:()->Unit) {
    //var imageUri = rememberSaveable { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    LaunchedEffect(userViewModel.userState.value.userInfo.address) {
        address = userViewModel.userState.value.userInfo.address
    }

    Log.d("USER_ADD",userViewModel.userState.value.userInfo.address)

    val ctx = LocalContext.current

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
                .padding(40.dp, vertical = 35.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        onBackButtonClicked.invoke()
                    }
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(60.dp, vertical = 0.dp),
            ) {
                Text(
                    modifier = Modifier
                        .padding(45.dp, 0.dp, 0.dp, 0.dp),
                    text = "Profile",
                    fontSize = 20.sp,
                    color = Colors.primaryOrange,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
//            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(8.dp)
                    .size(80.dp),
                colors = CardDefaults.cardColors(
                    containerColor =  Color(0xFFFAA73D),
                ),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_account_circle_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(110.dp)
                        .wrapContentSize()
                        .clickable {},
                        contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(top = 16.dp)
                    .size(25.dp)
            ){
                SmallFloatingActionButton(
                    onClick = { /* do something */ },
                    contentColor = Color.White,
                    containerColor = Colors.primaryOrange,
                    modifier = Modifier.size(25.dp)
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit profile picture")
                }
            }
        }
        Column(
            modifier = Modifier.padding(0.dp,10.dp,0.dp)
        ){
            Text(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 0.dp),
                text = "${userViewModel.userState.value.userInfo.firstName} ${userViewModel.userState.value.userInfo.lastName}",
                fontSize = 20.sp,
                color = Colors.primaryOrange,
                fontWeight = FontWeight.Bold,
            )
        }
        Column(modifier = Modifier
            .fillMaxWidth(.75f)
            .height(300.dp)
            .padding(0.dp, 20.dp, 0.dp)
            .shadow(10.dp, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFFFE6C7)),
        ){
            Column(modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp)
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(0.dp, 20.dp, 0.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "EMAIL",
                    color = Colors.primaryOrange,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp)
                .fillMaxWidth(.88f)
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color.White)
                .align(Alignment.CenterHorizontally)
                .height(40.dp)
            ){
                Text(
                    modifier = Modifier.padding(8.dp).testTag(TEST_TAG.PROFILE_EMAIL_TEXT)
                        .align(Alignment.CenterHorizontally),
                    text = userViewModel.userState.value.userInfo.email,
                    color = Color.Black,
                    maxLines = 1,
//                    fontSize = 14.sp,
//                    textAlign = TextAlign.Center
                )
//                Text(
//                    modifier = Modifier
//                        .padding(0.dp, 10.dp, 0.dp,10.dp)
//                        .align(Alignment.CenterHorizontally),
//                    text = "oliviat7@gmail.com",
//                    color = Color.Black,
////                    fontWeight = FontWeight.Bold
//                )
            }
            Column(modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp)
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier
                        .padding(0.dp, 20.dp, 0.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "ADDRESS",
                    color = Colors.primaryOrange,
                    fontWeight = FontWeight.Bold
                )
//                Spacer(modifier = Modifier.height(.dp))
            }
//            Column(modifier = Modifier
//                .padding(0.dp, 10.dp, 0.dp)
//                .fillMaxWidth(.88f)
//                .shadow(10.dp, RoundedCornerShape(10.dp))
//                .clip(RoundedCornerShape(10.dp))
//                .background(color = Color.White)
//                .align(Alignment.CenterHorizontally)
//            Column(modifier = Modifier
//                .padding(0.dp, 10.dp, 0.dp)
//                .fillMaxWidth(.88f)
//                .shadow(10.dp, RoundedCornerShape(10.dp))
//                .clip(RoundedCornerShape(10.dp))
//                .background(color = Color.White)
//                .align(Alignment.CenterHorizontally)
//                .height(40.dp)

//            ){
                TextField(
                    value = address,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp)
                        .fillMaxWidth(.88f)
                        .align(Alignment.CenterHorizontally)
                        .height(60.dp)
                        .shadow(10.dp, RoundedCornerShape(10.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Colors.white,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    label ={
                        if (address.isEmpty()) {Text("Enter Your Address", color = Colors.grey)}
                    },
//                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = {
                       address = it
                    }
                )

        }

        Spacer(modifier = Modifier.height(8.dp))


            Button(
                onClick = {
//                    if (!CredentialValidation.emailValid(email)) {
//                        Toast.makeText(ctx, "Invalid Email Address", Toast.LENGTH_SHORT).show()
//                        return@Button
//                    }
                    userViewModel.onUpdateUserAddress(AddressUpdateEvent.AddressUpdate(address))
                    Toast.makeText(ctx, "Address updated", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(13.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Colors.primaryOrange),
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp)
                    .fillMaxWidth(.75f)
                    .shadow(10.dp, RoundedCornerShape(10.dp))
                    .align(Alignment.CenterHorizontally)
                    .height(47.dp)
                    .testTag(TEST_TAG.PROFILE_ADDRESS_UPDATE_BUTTON),

                ){
                Text(text = "Update", color = Color.White)
//            }
        }
    }
}














@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(onBackButtonClicked = {})

}