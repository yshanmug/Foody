package com.chef.foody.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chef.foody.R
import com.chef.foody.domain.model.DeliveryOrderStatus
import com.chef.foody.util.Colors
import com.chef.foody.viewmodels.UserViewModel

@Composable
fun ChefOrderStatusPickupScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val userState by remember {userViewModel.userState}
    val chefName = userState.userInfo.firstName
    val activeOrderStatus by remember {userViewModel.chefActiveOrderStatus}

    when (val currentOrderStatus = activeOrderStatus) {
        null -> {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxSize()) {
                Text("No Active Order Found!", fontSize = 16.sp, fontStyle = FontStyle.Italic)
            }
        }
        else -> {
            val customerName = "${currentOrderStatus.customerFirstName} ${currentOrderStatus.customerLastName}"
            val time = currentOrderStatus.preparationTime
            val price = currentOrderStatus.price
            Column(
        //        verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
        //            .border(1.dp, Color.Magenta)
            ) {
                val commonModifier = Modifier.fillMaxWidth(.8f)
                Image(
                    painter = painterResource(id = R.drawable.checkmark),
                    contentDescription = "checkmark"
                )
                Text(
                    "Congratulations, Chef $chefName!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = commonModifier
                        .padding(vertical = 20.dp)
        //                .border(1.dp, Color.Black)
                )
                Text(
                    buildAnnotatedString {
                        val style = SpanStyle(color = Colors.primaryOrange, fontWeight = FontWeight.Bold)
                        append("Chef $chefName, your proposal has been accepted by ")
                        withStyle(style = style) {
                            append(customerName)
                        }
                        append(". They've ordered the following items: ")
                        withStyle(style = style) {
                            append(currentOrderStatus.cartInfo.map{ it->it.name}.joinToString(", "))
                        }
                        append(". The preparation time is estimated to be ")
                        withStyle(style = style) {
                            append("$time Min")
                        }
                        append(", and the total price quoted is ")
                        withStyle(style = style) {
                            append("$$price Dollars")
                        }
                        append(".")
                    },
                    modifier = commonModifier
                )
                StatusListWithData<DeliveryOrderStatus>(
                    statusList = listOf(
                        Pair("Order Confirmed",DeliveryOrderStatus.ORDER_CONFIRMED),
                        Pair("Preparing Food",DeliveryOrderStatus.PREPARING_FOOD),
                        Pair("Ready For Pickup", DeliveryOrderStatus.FOOD_IS_READY_FOR_PICKUP)
                    ),
                    clickableCallback = {
                        if (
                            (it == DeliveryOrderStatus.PREPARING_FOOD &&
                            currentOrderStatus.deliveryOrderStatus == DeliveryOrderStatus.ORDER_CONFIRMED)
                            ||
                            (it == DeliveryOrderStatus.FOOD_IS_READY_FOR_PICKUP &&
                            currentOrderStatus.deliveryOrderStatus == DeliveryOrderStatus.PREPARING_FOOD)
                        )
                            userViewModel.chefUpdateOrderStatus(it)
                    },
                    currentStatusIdx = when(currentOrderStatus.deliveryOrderStatus) {
                        DeliveryOrderStatus.ORDER_CONFIRMED -> 0
                        DeliveryOrderStatus.PREPARING_FOOD -> 1
                        DeliveryOrderStatus.FOOD_IS_READY_FOR_PICKUP -> 2
                        else -> -1
                    },
                    modifier = Modifier // don't set width
                        .padding(vertical = 20.dp)
        //                .border(1.dp, Color.Red)
                )
        //        Column(
        //            verticalArrangement = Arrangement.Center,
        //            horizontalAlignment = Alignment.CenterHorizontally,
        ////            modifier = Modifier.fillMaxSize().border(1.dp, Color.Blue)
        //        ) {
        //            StatusList(
        //                statusList = listOf(
        //                    "Order Confirmed",
        //                    "Preparing Food",
        //                    "Ready For Pickup"
        //                ),
        //                currentStatusIdx = 1,
        //                modifier = Modifier // don't set width
        //                    .padding(20.dp)
        //
        //                    .border(1.dp, Color.Red)
        //            )
        //        }
            }
        }
    }
}


@Preview
@Composable
fun PreviewChefOrderStatusPickupScreen() {
    ChefOrderStatusPickupScreen()
}


@Composable
fun ChefOrderStatusDoorScreen(modifier: Modifier = Modifier,userViewModel: UserViewModel= hiltViewModel()) {
    val userState by remember {userViewModel.userState}
    val chefName = userState.userInfo.firstName
    val activeOrderStatus by remember {userViewModel.chefActiveOrderStatus}
    when (val currentOrderStatus = activeOrderStatus) {
        null -> {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxSize()) {
                Text("No Active Order Found!", fontSize = 16.sp, fontStyle = FontStyle.Italic)
            }
        }
        else -> {
            val customerName = "${currentOrderStatus.customerFirstName} ${currentOrderStatus.customerLastName}"
            val time = currentOrderStatus.preparationTime
            val price = currentOrderStatus.price
            val address = currentOrderStatus.customerAddress
            Column(
                //        verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
                //            .border(1.dp, Color.Magenta)
            ) {
                val commonModifier = Modifier.fillMaxWidth(.8f)
                Image(
                    painter = painterResource(id = R.drawable.checkmark),
                    contentDescription = "checkmark"
                )
                Text(
                    "Congratulations, Chef $chefName!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = commonModifier
                        .padding(vertical = 20.dp)
                    //                .border(1.dp, Color.Black)
                )
                Text(
                    buildAnnotatedString {
                        val style = SpanStyle(color = Colors.primaryOrange, fontWeight = FontWeight.Bold)
                        append("Chef $chefName, your proposal has been accepted by ")
                        withStyle(style = style) {
                            append(customerName)
                        }
                        append(". They've ordered the following items: ")
                        withStyle(style = style) {
                            append(currentOrderStatus.cartInfo.map{ it->it.name}.joinToString(", "))
                        }
                        append(". The preparation time is estimated to be ")
                        withStyle(style = style) {
                            append("$time Min")
                        }
                        append(", and the total price quoted is ")
                        withStyle(style = style) {
                            append("$$price Dollars")
                        }
                        append(". Please deliver to their address: ")
                        withStyle(style = style) {
                            append(address)
                        }
                    },
                    modifier = commonModifier
                )
                StatusListWithData<DeliveryOrderStatus>(
                    statusList = listOf(
                        Pair("Order Confirmed",DeliveryOrderStatus.ORDER_CONFIRMED),
                        Pair("Preparing Food",DeliveryOrderStatus.PREPARING_FOOD),
                        Pair("Food is Ready For Delivery",DeliveryOrderStatus.FOOD_IS_READY_FOR_DELIVERY),
                        Pair("Delivered",DeliveryOrderStatus.DELIVERED)
                    ),
                    clickableCallback = {
                        if (
                            (it == DeliveryOrderStatus.PREPARING_FOOD &&
                                    currentOrderStatus.deliveryOrderStatus == DeliveryOrderStatus.ORDER_CONFIRMED)
                            ||
                            (it == DeliveryOrderStatus.FOOD_IS_READY_FOR_DELIVERY &&
                                    currentOrderStatus.deliveryOrderStatus == DeliveryOrderStatus.PREPARING_FOOD)
                            ||
                            (it == DeliveryOrderStatus.DELIVERED &&
                                    currentOrderStatus.deliveryOrderStatus == DeliveryOrderStatus.FOOD_IS_READY_FOR_DELIVERY)
                        )
                            userViewModel.chefUpdateOrderStatus(it)
                    },
                    currentStatusIdx = when(currentOrderStatus.deliveryOrderStatus) {
                        DeliveryOrderStatus.ORDER_CONFIRMED -> 0
                        DeliveryOrderStatus.PREPARING_FOOD -> 1
                        DeliveryOrderStatus.FOOD_IS_READY_FOR_DELIVERY -> 2
                        DeliveryOrderStatus.DELIVERED -> 3
                        else -> -1
                    },
                    modifier = Modifier // don't set width
                        .padding(vertical = 20.dp)
                    //                .border(1.dp, Color.Red)
                )
                StatusList(hasBackground = true,
                    statusList = listOf(
                    ),
                    clickableCallback = {
                        if(it==currentOrderStatus.deliveryOrderStatus.ordinal-1+1)
                            userViewModel.chefUpdateOrderStatus(DeliveryOrderStatus.entries[it+1])
                    },
                    currentStatusIdx = currentOrderStatus.deliveryOrderStatus.ordinal-1, // -1: NOT_ASSIGNED
                    modifier = Modifier // don't set width
                        .padding(vertical = 20.dp)
                    //                .border(1.dp, Color.Red)
                )
                //        Column(
                //            verticalArrangement = Arrangement.Center,
                //            horizontalAlignment = Alignment.CenterHorizontally,
                ////            modifier = Modifier.fillMaxSize().border(1.dp, Color.Blue)
                //        ) {
                //            StatusList(
                //                statusList = listOf(
                //                    "Order Confirmed",
                //                    "Preparing Food",
                //                    "Ready For Pickup"
                //                ),
                //                currentStatusIdx = 1,
                //                modifier = Modifier // don't set width
                //                    .padding(20.dp)
                //
                //                    .border(1.dp, Color.Red)
                //            )
                //        }
            }
        }
    }
}


@Preview
@Composable
fun PreviewChefOrderStatusDoorScreen() {
    ChefOrderStatusDoorScreen()
}
