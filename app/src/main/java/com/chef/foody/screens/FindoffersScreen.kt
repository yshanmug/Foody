package com.chef.foody.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W800
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chef.foody.R
import com.chef.foody.data.remote.CartInfo
import com.chef.foody.domain.model.DeliveryType
import com.chef.foody.events.AcceptOfferQuotationEvent
import com.chef.foody.presentation.chef.QuotationInfoList
import com.chef.foody.presentation.chef.QuotationList
import com.chef.foody.util.Colors
import com.chef.foody.viewmodels.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FindOffersScreen( //onNavigateToUserDelivery:() -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToDeliveryStatus:() -> Unit = {},
    onNavigateToPickupStatus:() -> Unit = {},
    userViewModel: UserViewModel = hiltViewModel()
){


    LaunchedEffect(key1 = Unit)
    {
        userViewModel.isOrderAcceptedByUser.collectLatest {
            if(it == DeliveryType.DOOR_DELIVERY)
                onNavigateToDeliveryStatus.invoke()
            else if(it == DeliveryType.TAKE_AWAY)
                onNavigateToPickupStatus.invoke()
        }
    }
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
    val chefQuotationList: QuotationList by userViewModel.chefQuotationList
    val chefQuotationInfoList: QuotationInfoList by userViewModel.chefQuotationInfoList

    val isAccepted = remember { mutableStateOf(false) }
    val ctx = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Colors.white), horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        if (chefQuotationList.data.isEmpty() || chefQuotationInfoList.data.isEmpty()){
            Column (modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center){

                Box(modifier = Modifier
                    .fillMaxWidth(),
                    contentAlignment = Alignment.Center

                ) {
                    LottieAnimation(
                        composition = composition,
                        progress = progress,
                        modifier = Modifier.size(125.dp)
                    )
                }
                Text(
                    text = "Please wait while our chefs review your order",
                    color = Color(0xFFFAA73D),
                    textAlign = TextAlign.Center,
                    fontWeight = W800,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }
       }
       else
       {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            )
            {
                items(chefQuotationInfoList.data)
                { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(color = Color(0xFFFFE6C7))
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    )
                    {
                        Spacer(modifier = Modifier.size(0.5.dp))
                        Image(
                            painter = painterResource(R.drawable.profile),
                            contentDescription = null,
                            modifier = Modifier
                                .size(130.dp)
                                .height(130.dp)
                        )
                        Spacer(modifier = Modifier.size(0.5.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        )
                        {
                            Text(
                                text = item.chef_first_name + " " + item.chef_last_name,
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            val foodItemsText = convertOrderToString(chefQuotationList.data)
                            val annotatedText = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Colors.primaryOrange)) {
                                    append("Food Items: ")
                                }
                                withStyle(style = SpanStyle(color = Colors.black)) {
                                    append(foodItemsText)
                                }
                            }
                            Text(
                                text = annotatedText,
                            )


                            //dollar and time
                            Row(
                                modifier = Modifier
                                    .height(20.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(25.dp)
                            )
                            {
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                )
                                {
                                    Image(
                                        painter = painterResource(id = R.drawable.dollars),
                                        contentDescription = "30 Dollars",
                                        modifier = Modifier
                                            .size(20.dp)
                                        //.weight(0.1f)
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(text = "${item.price} dollars")
                                }
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                )
                                {
                                    Image(
                                        painter = painterResource(id = R.drawable.clock),
                                        contentDescription = "30 Dollars",
                                        modifier = Modifier
                                            .size(20.dp)
                                        //.weight(0.1f)
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(text = "${item.preparation_time} Min")

                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            )
                            {
                                Button(
                                    onClick = {
                                        isAccepted.value = true
                                        userViewModel.onAcceptOfferQuotation(AcceptOfferQuotationEvent.AcceptOfferQuotation(item.quotation_id,true,item.delivery_mode))
                                        //onNavigateToUserDelivery()
                                        Toast.makeText(ctx, "Accept Offer", Toast.LENGTH_SHORT).show()
                                              },
                                    colors = ButtonDefaults.buttonColors(containerColor = Colors.primaryOrange),
                                    elevation = ButtonDefaults.buttonElevation(10.dp),
                                    modifier = Modifier.size(width = 100.dp, height = 40.dp)
                                ) {
                                    Text(text = "Accept", color = Colors.secondaryOrange)
                                }
                                Spacer(modifier = Modifier.width(6.dp)) // Add space between buttons
                                Button(
                                    onClick = {
                                        isAccepted.value = false
                                        userViewModel.onAcceptOfferQuotation(AcceptOfferQuotationEvent.AcceptOfferQuotation(item.quotation_id,false,item.delivery_mode))
                                        Toast.makeText(ctx, "Reject Offer", Toast.LENGTH_SHORT).show()
                                              },
                                    colors = ButtonDefaults.buttonColors(containerColor = Colors.primaryOrange),
                                    elevation = ButtonDefaults.buttonElevation(10.dp),
                                    modifier = Modifier.size(width = 100.dp, height = 40.dp)
                                ) {
                                    Text(text = "Reject", color = Colors.secondaryOrange)
                                }
                            }
                        }
                    }
                }
            }
        }
        LaunchedEffect(Unit) {
            while(true) {
                userViewModel.onLookingForQuotation()
                delay(5000)
            }
        }

    }
}

fun convertOrderToString(list: List<CartInfo>): String {
    val stringBuilder = StringBuilder()
    for ((index, obj) in list.withIndex()) {
        if (index > 0) {
            stringBuilder.append(", ")  // Add comma and space before appending the parameter
        }
        stringBuilder.append(obj.name)
    }
    return stringBuilder.toString()
}

//data class Profile(
//    val name: String,
//    val foodItems: String,
//    val price: Int,
//    val time: Int
//)

//@Preview(showBackground = true)
//@Composable
//fun FindOffersScreenPreview() {
//    FindOffersScreen()
//}