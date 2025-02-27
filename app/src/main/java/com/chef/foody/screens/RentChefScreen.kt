package com.chef.foody.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chef.foody.data.repository.UserRepositoryImpl
import com.chef.foody.util.Colors
import com.chef.foody.util.TEST_TAG
import com.chef.foody.viewmodels.UserViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentChefScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onNavigateToQuotationScreen:() -> Unit
    ) {
    var NotebyChef by remember { mutableStateOf(" ")}
    var isPlaceholderVisible by remember { mutableStateOf(true)}

    LaunchedEffect(key1 = "NavigateToQuotationScreen") {
       userViewModel.isChefRentOrderPosted.collect{isRentalChefOrderPosted->
            if(isRentalChefOrderPosted)
            {
                onNavigateToQuotationScreen.invoke()
            }
        }

    }



    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(horizontal = 16.dp, vertical = 8.dp)
            .padding(20.dp)
            .height(80.dp)
            .background(Colors.white),
        verticalArrangement = Arrangement.Center,
        )

    {
        Text(
            text = "Note For Chef: ",
            color =Colors.primaryOrange,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)

        )
//        Spacer(modifier = Modifier.size(1.dp))
        Text(
            text = "Please write a note to the chef specifying the ingredients you have at home and your desired meal. This note will help create a tailored menu for you. Include details such as the ingredients you have on hand and what you would like to eat. Your input will guide the Chef in Crafting a delicious meal just for you. ",
            color = Colors.black,
            textAlign = TextAlign.Justify,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 25.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(color = Colors.secondaryOrange)
                .height(250.dp)
//                        .padding(10.dp),
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        )

        {
            TextField(
                value = NotebyChef,
                onValueChange = {
                    NotebyChef = it
                },
                placeholder = {
                    Text("Enter your Note ...",color=Colors.grey)
                              },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Colors.white,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)).testTag(TEST_TAG.NOTE_TO_CHEF_TEXT_FIELD)
//                   .padding(bottom = 8.dp) // Add bottom padding to create space between fields
//                    .background(Colors.white)
                    .height(100.dp)
            )


            Button(
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Colors.primaryOrange),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp).testTag(TEST_TAG.FIND_A_CHEF_WITH_NOTE_BUTTON_CLICK),
//                modifier =  Modifier.size(width = 100.dp,height = 40.dp)
                onClick = { userViewModel.onRentAChef(NotebyChef) },

//            .border(1.dp, Color.Green)
            )
            {
                Text("Find A Chef", color = Colors.secondaryOrange, fontSize = 15.sp)
            }

        }


    }

}


//@Preview(showBackground = true)
//@Composable
//fun RentChefScreenPreview() {
//    RentAChefScreen()
//}



