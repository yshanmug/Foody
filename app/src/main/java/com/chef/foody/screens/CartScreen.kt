package com.chef.foody.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chef.foody.R
import com.chef.foody.data.remote.FoodInfo
import com.chef.foody.data.remote.deliveryMode
import com.chef.foody.events.CartEvent
import com.chef.foody.events.CartItem
import com.chef.foody.events.OrderFoodEvent
import com.chef.foody.presentation.cart.CartState
import com.chef.foody.util.TEST_TAG.PROCEED_TO_FIND_ORDERS_BUTTON_CLICKED
import com.chef.foody.viewmodels.UserViewModel
import currentRoute
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    onNavigateFindOffers:() -> Unit = {},
    userViewModel: UserViewModel = hiltViewModel())
{

    //val cartState: CartState by userViewModel.cartState
    val cartState: CartState by userViewModel.cartState
    Column (modifier = modifier
        .fillMaxSize()
    ) {
        if (cartState.cartList.isEmpty()) {
            // Show a message indicating that the cart is empty
            Text(
                text = "Your cart is empty! Let's find you something to eat.",
                color = Color(0xFFFAA73D),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        else {
            // Cart List UI
            LazyColumn(
                modifier = Modifier
                    .weight(0.7f)
            ) {
                items(cartState.cartList) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(color = Color(0xFFFFE6C7)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // image
                        Image(
                            painter = painterResource(item.foodItem.fileLocation), // change id to object
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                        )

                        // title
                        Text(text = item.title) // change to title of object
                        // increment buttons
                        Row(
                            modifier = Modifier
                                //.fillMaxSize()
                                .height(50.dp)
                                .padding(5.dp)
                                .clip(RoundedCornerShape(20.dp))

                                .background(color = Color(0xFFFAA73D)),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Minus button
                            Button(
                                onClick = {
                                    userViewModel.onCartEvent(
                                        CartEvent.ChangeCartQuantity(
                                            item,
                                            "-"
                                        )
                                    )
                                },
                                modifier = Modifier
                                //.weight(0.3f)
                                ,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFAA73D)
                                ),
                                contentPadding = PaddingValues(0.dp)
                                //Color(0xFFFFE6C7)
                            ) {
                                Text(
                                    text = "-",
                                    fontSize = 25.sp,
                                    color = Color(0XFFFFE6C7),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                )
                            }

                            // Counter text
                            Text(
                                text = item.quantity.toString(), // Update this value with your actual counter value
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500,
                                textAlign = TextAlign.Center,
                                color = Color(0XFFFFE6C7),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                //.weight(0.2f)
                            )

                            // Plus button
                            Button(
                                onClick = {
                                    userViewModel.onCartEvent(
                                        CartEvent.ChangeCartQuantity(
                                            item,
                                            "+"
                                        )
                                    )
                                },
                                modifier = Modifier
                                //.weight(0.3f)
                                ,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFAA73D)
                                ),
                                contentPadding = PaddingValues(0.dp)
                                //Color(0xFFFFE6C7)
                            ) {
                                Text(
                                    text = "+",
                                    fontSize = 20.sp,
                                    color = Color(0XFFFFE6C7),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                )
                            }
                        }

                        // delete button
                        Button(
                            onClick = { userViewModel.onCartEvent(CartEvent.DeleteCartItem(item)) }, // add delete button
                            modifier = Modifier
                                .width(40.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFAA73D)
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.delete_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(35.dp)
                                    .padding(5.dp)
                            )
                        }
                    }
                }
            }

        }
        // Delivery Options UI

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = Color(0xFF2A2A2A))
        ){
            var selectedOption by remember { mutableStateOf<deliveryMode>(deliveryMode.TAKE_AWAY) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Text
                Text(
                    text = "Delivery Options",
                    fontSize = 20.sp,
                    color = Color(0xFFFAA73D),
                    modifier = Modifier
                        .weight(0.8f)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W600
                )
                // Two buttons
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    // Door Delivery
                    Button(
                        onClick = { selectedOption = deliveryMode.DOOR_DELIVERY },
                        colors = ButtonDefaults.buttonColors(containerColor = if (selectedOption == deliveryMode.DOOR_DELIVERY) Color(0xFFFAA73D) else Color(0XFFFFE6C7))
                    ) {
                        Text(text = "Door Delivery", color = Color(0xFF2A2A2A))
                    }
                    // Take Away
                    Button(
                        onClick = { selectedOption = deliveryMode.TAKE_AWAY },
                        colors = ButtonDefaults.buttonColors(containerColor = if (selectedOption == deliveryMode.TAKE_AWAY) Color(0xFFFAA73D) else Color(0XFFFFE6C7))
                    ) {
                        Text(text = "Take Away", color = Color(0xFF2A2A2A))
                    }
                }
                // Final button
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                        .background(color = Color(0xFFFAA73D))
                ) {
                    Button(
                        onClick = {
                            userViewModel.onProceedToFindOrder(
                                OrderFoodEvent.OrderFood(
                                    email = userViewModel.userState.value.userInfo.email,
                                    cartList = convertToFoodInfoList(cartState.cartList),
                                    deliveryMode = selectedOption)
                            )
                            onNavigateFindOffers()},
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFAA73D)),
                        modifier = Modifier.align(Alignment.CenterHorizontally).testTag(PROCEED_TO_FIND_ORDERS_BUTTON_CLICKED)
                    ) {
                        Text(text = "Proceed to Find Offers", fontSize = 20.sp)
                    }
                }
            }
        }

    }
}

fun convertToFoodInfoList(cartItems: List<CartItem>): List<FoodInfo> {
    return cartItems.map { cartItem ->
        FoodInfo(
            name = cartItem.foodItem.name,
            quantity = cartItem.quantity,
            type = cartItem.foodItem.foodType
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun CartScreenPreview() {
//    CartScreen(Modifier)
//}