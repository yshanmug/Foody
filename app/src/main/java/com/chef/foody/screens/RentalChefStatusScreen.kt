package com.chef.foody.screens
import android.util.Log
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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chef.foody.R
import com.chef.foody.data.remote.ChefOrderStatus
import com.chef.foody.domain.model.DeliveryOrderStatus
import com.chef.foody.domain.model.RentalChefOrderStatus
import com.chef.foody.util.Colors
import com.chef.foody.viewmodels.UserViewModel
import kotlinx.coroutines.delay


@Composable
fun RentalChefStatusScreen(
    modifier: Modifier = Modifier,
    currentOrderID:Int = -1,
    userViewModel: UserViewModel = hiltViewModel()
) {

    Log.d("CHEF_RENTAL_CHECK","FROM SCREEN $currentOrderID")
    val userState by remember {userViewModel.userState}
    val chefName = userState.userInfo.firstName
    val activeOrderStatus by remember {userViewModel.rentalChefActiveOrderStatus}
    LaunchedEffect(Unit) {
        while (true)
        {
            userViewModel.getRentChefOrderStatusFromChef(currentOrderID)
            delay(5000)
        }

    }

            val customerName = "${activeOrderStatus?.customerFirstName} ${activeOrderStatus?.customerFirstName}"
//            val menu
            val time = activeOrderStatus?.preparationTime
            val price =activeOrderStatus?.price
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
                        append(" $customerName's Note: ")
                        withStyle(style = style) {
                            append(activeOrderStatus?.userNote)
                        }
                        append(" Your menu: ")
                        withStyle(style = style) {
                            append(activeOrderStatus?.menu)
                        }
                        append(" and the preparation time is estimated to be ")
                        withStyle(style = style) {
                            append("$time Min")
                        }
                        append(", and the total price quoted is ")
                        withStyle(style = style) {
                            append("$$price Dollars")
                        }
                        append(". Please head to their address at ")
                        withStyle(style = style) {
                            append(activeOrderStatus?.customerAddress)
                        }
                        append(" to prepare the meal onsite.")

                    },
                    modifier = commonModifier
                )
                StatusListWithData<RentalChefOrderStatus>(
                    statusList = listOf(
                        Pair("Order Confirmed", RentalChefOrderStatus.ORDER_CONFIRMED),
                        Pair("On The Way to Client’s Home", RentalChefOrderStatus.ON_THE_WAY_TO_YOUR_HOME),
                        Pair("Reached Client’s Home", RentalChefOrderStatus.CHEF_REACHED)
                    ),
                    clickableCallback = {
                        if ((it == RentalChefOrderStatus.ON_THE_WAY_TO_YOUR_HOME &&
                                    activeOrderStatus?.chefOrderStatus == RentalChefOrderStatus.ORDER_CONFIRMED) ||
                            (it == RentalChefOrderStatus.CHEF_REACHED &&
                                    activeOrderStatus?.chefOrderStatus == RentalChefOrderStatus.ON_THE_WAY_TO_YOUR_HOME))
                            userViewModel.rentChefUpdateOrderStatus(currentOrderID,it)

                    },
                    currentStatusIdx = when(activeOrderStatus?.chefOrderStatus) {
                        RentalChefOrderStatus.ORDER_CONFIRMED -> 0
                        RentalChefOrderStatus.ON_THE_WAY_TO_YOUR_HOME -> 1
                        RentalChefOrderStatus.CHEF_REACHED -> 2
                        else -> -1
                    },
                    modifier = Modifier // don't set width
                        .padding(vertical = 20.dp)
                )

    }

}