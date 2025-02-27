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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chef.foody.R
import com.chef.foody.domain.model.DeliveryOrderStatus
import com.chef.foody.presentation.customer.GetCustomerCheckOderStatus
import com.chef.foody.util.Colors
import com.chef.foody.viewmodels.UserViewModel
import kotlinx.coroutines.delay

@Composable
fun UserOrderDeliveryStatusScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel()
) {
    userViewModel.resetLookingForQuotationList()
    LaunchedEffect(Unit) {
        userViewModel.onCustomerCheckOrderStatus()
    }
    val customerCheckOrderStatusList: GetCustomerCheckOderStatus by userViewModel.customerCheckOrderStatusList

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val commonModifier = Modifier.fillMaxWidth(.8f)
        Image(
            painter = painterResource(id = R.drawable.checkmark),
            contentDescription = "checkmark"
        )
        Text(
            "Hey ${userViewModel.userState.value.userInfo.firstName},",
            textAlign = TextAlign.Center
        )
        Text(
            "Your Order is Confirmed!",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = commonModifier
                .padding(vertical = 20.dp)
        )

            Text(
                buildAnnotatedString {
                    val style =
                        SpanStyle(color = Colors.primaryOrange, fontWeight = FontWeight.Bold)
                    append("Your food is being prepared by Chef")
                    withStyle(style = style) {
                        append(" ${customerCheckOrderStatusList.data.chef_first_name} ${customerCheckOrderStatusList.data.chef_last_name}")
                    }
                    append(". Please wait for a few minutes. Once your order is ready, we will notify you. You can check the order status below.")
                },
                modifier = commonModifier
            )

            StatusList(
                hasBackground = false,
                statusList = listOf(
                    "Order Confirmed",
                    "Preparing Food",
                    "Food is Ready For Delivery",
                    "Delivered"
                ),
                currentStatusIdx = when(customerCheckOrderStatusList.data.order_status){
                    DeliveryOrderStatus.ORDER_CONFIRMED -> 0
                    DeliveryOrderStatus.PREPARING_FOOD -> 1
                    DeliveryOrderStatus.FOOD_IS_READY_FOR_DELIVERY ->2
                    DeliveryOrderStatus.DELIVERED ->3
                    else -> 0
                }
                ,
                modifier = Modifier // don't set width
                    .padding(vertical = 20.dp)
            )
        LaunchedEffect(Unit) {
            while (true) {
                userViewModel.onCustomerCheckOrderStatus()
                delay(5000)
            }
        }
    }
}


//    val customerCheckOrderStatusList: GetCustomerCheckOderStatus by userViewModel.customerCheckOrderStatusList
//    val customerTextInfo = convertIfoToString(customerCheckOrderStatusList.data)
//    LazyColumn() {
//        items(customerCheckOrderStatusList.data)
//        { item ->
//            Text(text = item.chef_first_name)
//        }
//    }




@Preview(showBackground = true)
@Preview
@Composable
fun UserOrderDeliveryStatusScreenPreview() {
    UserOrderDeliveryStatusScreen()
}
