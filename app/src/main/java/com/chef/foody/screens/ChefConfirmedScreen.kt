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
import com.chef.foody.domain.model.RentalOrderStatus
import com.chef.foody.presentation.customer.CurrentRentalOrderStatusList
import com.chef.foody.util.Colors
import com.chef.foody.viewmodels.UserViewModel
import kotlinx.coroutines.delay


@Composable
fun ChefConfirmedScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        userViewModel.onCurrentRentalOrderStatus()
    }
    val currentRentalOrderStatusList: CurrentRentalOrderStatusList by userViewModel.currentRentalOrderStatusList
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
            "Hey \"${userViewModel.userState.value.userInfo.firstName},",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = commonModifier
                .padding(vertical = 20.dp)
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
                    append("Your Chef")
                    withStyle(style = style) {
                        append("${ currentRentalOrderStatusList.data.chef_first_name} ${ currentRentalOrderStatusList.data.chef_last_name}")
                    }
                    append("is now on route to your home. You can track their progress below. Once the chef arrive at your doorstep, the status will change to")
                    withStyle(style = style) {
                        append("Chef Reached.")
                    }
                },
                modifier = commonModifier
            )
            StatusList(
                hasBackground = false,
                statusList = listOf(
                    "Order Confirmed",
                    "On the Way to Client's Home",
                    "Reached Client's Home"
                ),
                currentStatusIdx = when(currentRentalOrderStatusList.data.order_status)
                {
                    RentalOrderStatus.ORDER_CONFIRMED -> 0
                    RentalOrderStatus.ON_THE_WAY_TO_YOUR_HOME -> 1
                    RentalOrderStatus.CHEF_REACHED -> 2
                    else -> 0
                },
                modifier = Modifier // don't set width
                    .padding(vertical = 20.dp)
            )
        }
        LaunchedEffect(Unit) {
            while (true) {
                userViewModel.onCurrentRentalOrderStatus()
                delay(5000)
            }
        }

}









@Preview
@Composable
fun PreviewChefConfirmedScreen() {
    ChefConfirmedScreen()
}

