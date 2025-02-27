package com.chef.foody.screens

import UserType
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chef.foody.R
import com.chef.foody.util.Colors
import com.chef.foody.util.TEST_TAG
import com.chef.foody.viewmodels.UserViewModel



@Composable
fun TopBarGreeting(userViewModel: UserViewModel = hiltViewModel(),onProfileIconClicked:() -> Unit = {}) {

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(80.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = if(userViewModel.currentUserType.value == UserType.COOK) "Hello, ${userViewModel.userState.value.userInfo.firstName} " else "Hello, Chef ${userViewModel.userState.value.userInfo.firstName} ",
                    color = Color(0xFFFAA73D),
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = if(userViewModel.currentUserType.value == UserType.COOK) "Cook virtually, dine deliciously!" else "Cook with Passion, Serve with Pride!",
                    color = Color(0xFFFAA73D),
                    textAlign = TextAlign.Left,
                    fontSize = 12.sp
                )
            }
            Box(modifier = Modifier
                .testTag(TEST_TAG.PROFILE_SCREEN_ICON_CLICKED)
                .padding(16.dp)
                .size(56.dp)
                .clickable {
                    onProfileIconClicked()
                },
                contentAlignment = Alignment.Center) {
                Box(modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(color = Color(0xFFFAA73D)),
                    contentAlignment = Alignment.Center
                ) {
                    // Image of user
                    Image(
                        painter = painterResource(id = R.drawable.baseline_person_24),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun OrderTopBar(arrow: Boolean, titleText: String, cart: Boolean){

    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(35.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Arrow
        if (arrow){Image(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
            //.weight(0.1f)
        )}

        //Text
        Text(
            text = titleText,
            fontSize = 24.sp,
            color = Color(0xFFFAA73D),
            modifier = Modifier
                .weight(0.8f)
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center

        )
        //ShoppingCart
        if (cart){
            Image(
                painter = painterResource(id = R.drawable.icon__shopping_cart_),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .weight(0.1f)
            )
        }

    }
}

//@Composable
//fun BottomNavigation(function: () -> Unit)
//{
//    Box(modifier = Modifier
//        .padding(20.dp)){
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clip(RoundedCornerShape(30.dp))
//                .background(color = Color(0xFFFAA73D))
//                .height(65.dp)
//                .padding(10.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxSize()
////                .padding(10.dp)
//                ,
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                // button 1 - Cook
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .fillMaxHeight()
//                        .clip(RoundedCornerShape(30.dp))
//                        .background(color = Color(0xFFFFE6C7))
//                        .width(150.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
//                        Icon(painter = painterResource(id = R.drawable.pot_icon_filled), contentDescription = "Cook Icon")
//                        Text(text = "Cook")
//                    }
//
//                }
//
//                // button 2 - Chef
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .fillMaxHeight()
//                        .clip(RoundedCornerShape(16.dp))
//                        .width(150.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
//                        Icon(painter = painterResource(id = R.drawable.chef_icon), contentDescription = "Chef Icon")
//                        Text(text = "Chef")
//                    }
//                }
//            }
//        }
//    }
//
//}

@Composable
fun StatusList(
    hasBackground: Boolean,
    statusList: List<String>,
    currentStatusIdx: Int,
    modifier: Modifier = Modifier,
    clickableCallback: (Int)->Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        val bulletsize = 10.dp
        val bulletpadding = 8.dp
        statusList.forEachIndexed {
            idx, it ->
            val color = if (idx<=currentStatusIdx) Colors.primaryOrange else Colors.secondaryOrange
            if (idx!=0) {
                Row(
                    modifier = Modifier
                        .padding(bulletsize / 2 - 1.dp, 0.dp, 0.dp, 0.dp)
                        .height(IntrinsicSize.Min)
                ) {
                    Divider(
                        color = color,
                        modifier = Modifier
                            .width(2.dp)
                            .height(50.dp)
                    )
                }
            }
            Row(modifier = Modifier
                .clickable(
                    interactionSource = remember {MutableInteractionSource()},
                    indication = null
                ) { clickableCallback(idx) }
//                .border(1.dp, Color.Red)
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_circle_24),
                    "bullet point",
                    colorFilter = ColorFilter.tint(color),
                    modifier = Modifier
                        .padding(0.dp, 0.dp, bulletpadding, 0.dp)
                        .size(bulletsize)
                        .align(Alignment.CenterVertically)
                )
                Text(text = it, modifier = (if (idx == currentStatusIdx && hasBackground)
                    Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(color)
                else Modifier)
                    .align(Alignment.CenterVertically)
                    .padding(4.dp)
                )
            }
        }
    }
}


@Composable
fun <T> StatusListWithData (
    statusList: List<Pair<String, T>>,
    currentStatusIdx: Int,
    modifier: Modifier = Modifier,
    clickableCallback: (T)->Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        val bulletsize = 10.dp
        val bulletpadding = 8.dp
        statusList.forEachIndexed {
                idx, it ->
            val color = if (idx<=currentStatusIdx) Colors.primaryOrange else Colors.secondaryOrange
            if (idx!=0) {
                Row(
                    modifier = Modifier
                        .padding(bulletsize / 2 - 1.dp, 0.dp, 0.dp, 0.dp)
                        .height(IntrinsicSize.Min)
                ) {
                    Divider(
                        color = color,
                        modifier = Modifier
                            .width(2.dp)
                            .height(50.dp)
                    )
                }
            }
            Row(modifier = Modifier
                .clickable(
                    interactionSource = remember {MutableInteractionSource()},
                    indication = null
                ) { clickableCallback(it.second) }
//                .border(1.dp, Color.Red)
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_circle_24),
                    "bullet point",
                    colorFilter = ColorFilter.tint(color),
                    modifier = Modifier
                        .padding(0.dp, 0.dp, bulletpadding, 0.dp)
                        .size(bulletsize)
                        .align(Alignment.CenterVertically)
                )
                Text(text = it.first, modifier = (if (idx == currentStatusIdx) Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(color)
                else Modifier)
                    .align(Alignment.CenterVertically)
                    .padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun LoopAnimation(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.loading
        )
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    LottieAnimation(
        modifier = Modifier.size(125.dp),
        composition = composition, progress = progress
    )
}