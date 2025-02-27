package com.chef.foody.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chef.foody.R
import com.chef.foody.util.TEST_TAG
import com.chef.foody.viewmodels.UserViewModel

@Composable
fun BottomBar(
    onChefScreenNavigation:() -> Unit,
    onCookScreenNavigation:() -> Unit,
    userViewModel: UserViewModel = hiltViewModel()
) {
//    val isSelected = remember{ mutableStateOf(UserType.COOK)}
    Box(modifier = Modifier
        .padding(20.dp)){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(color = Color(0xFFFAA73D))
                .height(65.dp)
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
//                .padding(10.dp)
                ,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // button 1 - Cook
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight().testTag(TEST_TAG.COOK_BUTTON_CLICK)
                        .clip(RoundedCornerShape(30.dp))
                        .background(
                            color = if (userViewModel.currentUserType.value == UserType.COOK) Color(
                                0xFFFFE6C7
                            ) else Color.Transparent
                        )
                        .width(150.dp)
                        .clickable {
                            userViewModel.currentUserType.value = UserType.COOK
                            onCookScreenNavigation.invoke()

                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
                        Icon(painter = painterResource(id = if(userViewModel.currentUserType.value == UserType.COOK) R.drawable.pot_icon_filled else R.drawable.pot_icon), contentDescription = "Cook Icon")
                        Text(text = "Cook")
                    }
                }
                // button 2 - Chef
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(30.dp))
                        .background(
                            color = if (userViewModel.currentUserType.value == UserType.CHEF) Color(
                                0xFFFFE6C7
                            ) else Color.Transparent
                        )
                        .width(150.dp).testTag(TEST_TAG.CHEF_BUTTON_CLICK)
                        .clickable {
                            userViewModel.currentUserType.value = UserType.CHEF
                            onChefScreenNavigation.invoke()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
                        Icon(painter = painterResource(id = if(userViewModel.currentUserType.value == UserType.CHEF) R.drawable.chef_icon_filled else R.drawable.chef_icon), contentDescription = "Chef Icon")
                        Text(text = "Chef")
                    }
                }
            }
        }
    }
}